package edu.datastreams.analytics.dto;

public record DashboardCheckinsDto(
        String timeframe,
        int currentBurst,
        boolean newRecord
) {}