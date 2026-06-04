package edu.datastreams.analytics.query;

import edu.datastreams.analytics.dto.DashboardCrowdUpdate;
import edu.datastreams.analytics.model.CrowdDensityEvent;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OverCrowdingDetectionService {

    private final SimpMessagingTemplate messagingTemplate;

    public OverCrowdingDetectionService(
            SimpMessagingTemplate messagingTemplate
    ) {
        this.messagingTemplate = messagingTemplate;
    }

    private final Map<String, Integer> capacities = Map.of(
            "Main Stage", 500,
            "Techno Arena", 200,
            "House Tent", 800,
            "Food Court", 600
    );

    public void process(CrowdDensityEvent event) {

        int capacity =
                capacities.getOrDefault(
                        event.stage(),
                        1000
                );

        double percentage =
                (event.peopleCount() * 100.0)
                        / capacity;

        boolean overcrowded =
                event.peopleCount() > capacity;

        DashboardCrowdUpdate update =
                new DashboardCrowdUpdate(
                        event.stage(),
                        event.peopleCount(),
                        capacity,
                        percentage,
                        overcrowded
                );

        System.out.println(
                "[KAFKA] Received crowd event -> " +
                event
        );

        messagingTemplate.convertAndSend(
                "/topic/crowd",
                update
        );

        System.out.println(
                "[WEBSOCKET] Sent dashboard update -> " +
                update
        );

        if (overcrowded) {

            System.out.println(
                    "⚠ OVERCROWDED: " +
                    event.stage() +
                    " | " +
                    event.peopleCount() +
                    "/" +
                    capacity +
                    " (" +
                    String.format("%.1f", percentage) +
                    "%)"
            );
        }
    }
}