package edu.datastreams.wristband_generator.service;

import edu.datastreams.wristband_generator.model.WristbandEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class WristbandGeneratorService {

    private final KafkaTemplate<String, WristbandEvent> kafkaTemplate;

    private final Random random = new Random();

    private final List<String> zones = List.of(
            "Main Stage",
            "Techno Arena",
            "House Tent",
            "Food Court",
            "VIP Area",
            "Camping"
    );

    public WristbandGeneratorService(
            KafkaTemplate<String, WristbandEvent> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 1000)
    public void generateWristbandScan() {

        WristbandEvent event = new WristbandEvent(
                "WB-" + random.nextInt(1000),
                "ATT-" + UUID.randomUUID().toString().substring(0, 8),
                zones.get(random.nextInt(zones.size())),
                random.nextBoolean() ? "ENTRY" : "EXIT",
                LocalDateTime.now()
        );

        kafkaTemplate.send(
                "wristband-events",
                event.wristbandId(),
                event
        );

        System.out.println("Sent wristband event -> " + event);
    }
}