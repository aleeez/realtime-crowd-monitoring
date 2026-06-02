package edu.datastreams.analytics.query;

import edu.datastreams.analytics.model.CrowdDensityEvent;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OverCrowdingDetectionService {

    private final Map<String, Integer> capacities = Map.of(
            "Main Stage", 500,
            "Techno Arena", 200,
            "House Tent", 800,
            "Food Court", 600
    );

    public void process(CrowdDensityEvent event) {

        Integer capacity =
                capacities.getOrDefault(event.stage(), Integer.MAX_VALUE);

        if (event.peopleCount() > capacity) {

            System.out.println(
                    "[OVERCROWDING] " +
                    event.stage() +
                    " has " +
                    event.peopleCount() +
                    " people. Capacity: " +
                    capacity
            );
        }
    }
}