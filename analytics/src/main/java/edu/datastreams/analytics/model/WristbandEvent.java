package edu.datastreams.analytics.model;

import java.time.LocalDateTime;

public record WristbandEvent(
        String wristbandId,
        String attendeeId,
        String zone,
        String action,
        LocalDateTime timestamp
) {
}