package com.cityconnect.participation.web.dto;

import java.util.UUID;

public class ActivityRegistrationsResponse {

    private UUID activityId;

    private long confirmed;

    private long waitlisted;

    public UUID getActivityId() {
        return activityId;
    }

    public long getConfirmed() {
        return confirmed;
    }

    public long getWaitlisted() {
        return waitlisted;
    }

    public ActivityRegistrationsResponse(UUID activityId, long confirmed, long waitlisted) {
        this.activityId = activityId;
        this.confirmed = confirmed;
        this.waitlisted = waitlisted;
    }
}
