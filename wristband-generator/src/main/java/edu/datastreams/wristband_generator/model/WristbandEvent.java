package edu.datastreams.wristband_generator.model;

import java.time.LocalDateTime;

public record WristbandEvent(
        String wristbandId,
        String attendeeId,
        String ticketType,
        String action,
        LocalDateTime timestamp
) {}