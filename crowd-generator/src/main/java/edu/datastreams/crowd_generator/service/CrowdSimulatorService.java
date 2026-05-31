package edu.datastreams.crowd_generator.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import edu.datastreams.crowd_generator.model.CrowdDensityEvent;

@Service
public class CrowdSimulatorService {

    private final List<String> stages = List.of(
            "Main Stage",
            "Techno Arena",
            "House Tent",
            "Food Court"
    );

    private final KafkaTemplate<String, CrowdDensityEvent> kafkaTemplate;

    private final Random random = new Random();

    public CrowdSimulatorService(
            KafkaTemplate<String, CrowdDensityEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 2000)
    public void generateCrowdData() {

        String stage = stages.get(
                random.nextInt(stages.size())
        );

        int peopleCount =
                200 + random.nextInt(1800);

        CrowdDensityEvent event =
                new CrowdDensityEvent(
                        stage,
                        peopleCount,
                        LocalDateTime.now()
                );

        kafkaTemplate.send(
                "crowd-density",
                event
        );

        System.out.println(
                "Sent event -> " + event
        );
    }
}