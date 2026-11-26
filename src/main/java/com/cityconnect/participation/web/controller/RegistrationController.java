package com.cityconnect.participation.web.controller;

import com.cityconnect.participation.registration.service.RegistrationService;
import com.cityconnect.participation.web.dto.ActivityRegistrationsResponse;
import com.cityconnect.participation.web.dto.RegistrationRequest;
import com.cityconnect.participation.web.dto.RegistrationResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }


    @PostMapping("/registrations")
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationResponse register (@Valid @RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }


    @DeleteMapping("/registrations/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable UUID id){
        registrationService.cancel(id);
    }


    @GetMapping("/activities/{activityId}/registrations")
    public ActivityRegistrationsResponse getSummary(@PathVariable UUID activityId){
        return registrationService.getSummary(activityId);
    }
}
