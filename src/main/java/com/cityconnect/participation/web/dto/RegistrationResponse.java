package com.cityconnect.participation.web.dto;

import com.cityconnect.participation.registration.model.RegistrationStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class RegistrationResponse {

    private UUID id;

    private UUID userId;

    private UUID activityId;

    private RegistrationStatus status;

    private LocalDateTime registeredAt;

    public RegistrationResponse(UUID id, UUID userId, UUID activityId, RegistrationStatus status, LocalDateTime registeredAt) {
        this.id = id;
        this.userId = userId;
        this.activityId = activityId;
        this.status = status;
        this.registeredAt = registeredAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getActivityId() {
        return activityId;
    }

    public RegistrationStatus getStatus() {
        return status;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

}
