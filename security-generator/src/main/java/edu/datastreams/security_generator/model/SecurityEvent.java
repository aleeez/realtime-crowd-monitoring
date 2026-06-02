package edu.datastreams.security_generator.model;

import java.time.LocalDateTime;

public record SecurityEvent(
        String zone,
        String incidentType,
        int severity,
        LocalDateTime timestamp
) {
}