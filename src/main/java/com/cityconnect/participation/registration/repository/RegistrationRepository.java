package com.cityconnect.participation.registration.repository;

import com.cityconnect.participation.registration.model.Registration;
import com.cityconnect.participation.registration.model.RegistrationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, UUID> {

    @Query("select count(r) from Registration as r where r.activityId = :activityId and r.status = :status")
    long countByActivityAndStatus(@Param("activityId") UUID activityId, @Param("status") RegistrationStatus status);

    @Query("select count(r) >0 from Registration  as r where r.userId = :userId and r.activityId = :activityId")
    boolean existsByUserAndActivity(@Param("userId") UUID userId, @Param("activityId") UUID activityId);


    @Query("select r from Registration as r where r.activityId = :activityId order by r.registeredAt")
    List<Registration> findAllByActivity(@Param("activityId") UUID activityId);

    @Query("select r from Registration as r where r.activityId = :activityId and r.status = :status order by r.registeredAt")
    List<Registration> findByActivityAndStatusOrdered(@Param("activityId") UUID activityId, @Param("status") RegistrationStatus status);

    @Query("SELECT r FROM Registration r WHERE r.userId = :userId ORDER BY r.registeredAt DESC")
    List<Registration> findAllByUser(@Param("userId") UUID userId);

}
