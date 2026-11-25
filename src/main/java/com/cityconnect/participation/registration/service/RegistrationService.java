package com.cityconnect.participation.registration.service;

import com.cityconnect.participation.exception.AlreadyRegisteredException;
import com.cityconnect.participation.exception.RegistrationNotFoundException;
import com.cityconnect.participation.registration.model.Registration;
import com.cityconnect.participation.registration.model.RegistrationStatus;
import com.cityconnect.participation.registration.repository.RegistrationRepository;
import com.cityconnect.participation.web.dto.ActivityRegistrationsResponse;
import com.cityconnect.participation.web.dto.RegistrationRequest;
import com.cityconnect.participation.web.dto.RegistrationResponse;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
public class RegistrationService {

    private static final Logger log = LoggerFactory.getLogger(RegistrationService.class);


    private final RegistrationRepository registrationRepository;

    public RegistrationService(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @Transactional
    public RegistrationResponse register(RegistrationRequest request) {
        if (registrationRepository.existsByUserAndActivity(request.getUserId(), request.getActivityId())) {
            throw new AlreadyRegisteredException("The user is already registered for this activity.");
        }
        long confirmed = registrationRepository.countByActivityAndStatus(request.getActivityId(), RegistrationStatus.CONFIRMED);

        Registration registration = new Registration();
        registration.setUserId(request.getUserId());
        registration.setActivityId(request.getActivityId());
        registration.setStatus(confirmed < request.getCapacity() ? RegistrationStatus.CONFIRMED : RegistrationStatus.WAITLISTED);
        registration.setRegisteredAt(LocalDateTime.now());

        Registration saved = registrationRepository.save(registration);
        log.info("User {} registered for activity {} with status {}", saved.getUserId(), saved.getActivityId(),saved.getStatus());

        return toResponse(saved);
    }

    @Transactional
    public void cancel(UUID registrationId){
        Registration registration= registrationRepository.findById(registrationId)
                .orElseThrow(() -> new RegistrationNotFoundException("Registration not found."));

        boolean freedSpot = registration.getStatus() == RegistrationStatus.CONFIRMED;
        registrationRepository.delete(registration);
        log.info("Registration {} cancelled",registrationId);

        if(freedSpot){
            promoteFirstWaitlisted(registration.getActivityId());
        }
    }


    public ActivityRegistrationsResponse getSummary(UUID activityId){
        long confirmed = registrationRepository.countByActivityAndStatus(activityId,RegistrationStatus.CONFIRMED);
        long waitlisted = registrationRepository.countByActivityAndStatus(activityId,RegistrationStatus.WAITLISTED);
        return new ActivityRegistrationsResponse(activityId,confirmed,waitlisted);

    }


    private void promoteFirstWaitlisted(UUID activityId){
        registrationRepository.findByActivityAndStatusOrdered(activityId,RegistrationStatus.WAITLISTED)
                .stream()
                .findFirst()
                .ifPresent(next ->{
                    next.setStatus(RegistrationStatus.CONFIRMED);
                registrationRepository.save(next);
                log.info("Registration {} promoted from the waiting list", next.getId());
                });

    }

    private RegistrationResponse toResponse(Registration registration){
         return new RegistrationResponse(
                 registration.getId(),
                 registration.getUserId(),
                 registration.getActivityId(),
                 registration.getStatus(),
                 registration.getRegisteredAt());
    }


}
