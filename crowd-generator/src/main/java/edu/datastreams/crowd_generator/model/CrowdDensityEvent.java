package edu.datastreams.crowd_generator.model;

import java.time.LocalDateTime;

public record CrowdDensityEvent(
        String stage,
        Integer peopleCount,
        LocalDateTime timestamp
) {}