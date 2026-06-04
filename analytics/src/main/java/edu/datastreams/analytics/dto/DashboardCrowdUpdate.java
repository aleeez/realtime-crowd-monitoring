package edu.datastreams.analytics.dto;

public record DashboardCrowdUpdate(
        String stage,
        int peopleCount,
        int capacity,
        double percentage,
        boolean overcrowded
) {}