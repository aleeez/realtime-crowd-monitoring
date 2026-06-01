package edu.datastreams.analytics.service;

import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import edu.datastreams.analytics.model.CrowdDensityEvent;

@Service
public class CrowdConsumer {

    private static final Map<String, Integer> CAPACITIES = Map.of(
        "Main Stage", 1500,
        "Techno Arena", 1000,
        "House Tent", 800,
        "Food Court", 1200
    );

    @KafkaListener(
        topics = "crowd-density",
        groupId = "crowd-analytics-group"
    )
    public void consume(CrowdDensityEvent event) {

        System.out.println(
            "Received: " +
            event.stage() +
            " -> " +
            event.peopleCount()
        );

        Integer capacity = CAPACITIES.get(event.stage());

        if (capacity != null &&
            event.peopleCount() > capacity) {

            System.out.println(
                "🚨 OVERCROWDING DETECTED: " +
                event.stage() +
                " (" +
                event.peopleCount() +
                "/" +
                capacity +
                ")"
            );
        }
    }
}