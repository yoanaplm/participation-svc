package com.cityconnect.participation.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class RegistrationRequest {
    @NotNull(message = "User id required.")
    private UUID userId;
    @NotNull(message = "Activity id required.")
    private UUID activityId;
    @NotNull(message = "Capacity required.")
    @Min(value=1,message = "Capacity must be at least 1.")
    private Integer capacity;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getActivityId() {
        return activityId;
    }

    public void setActivityId(UUID activityId) {
        this.activityId = activityId;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
