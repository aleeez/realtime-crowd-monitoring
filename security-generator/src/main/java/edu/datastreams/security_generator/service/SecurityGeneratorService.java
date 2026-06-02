package edu.datastreams.security_generator.service;

import edu.datastreams.security_generator.model.SecurityEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class SecurityGeneratorService {

    private final KafkaTemplate<String, SecurityEvent> kafkaTemplate;

    private final Random random = new Random();

    private final List<String> zones = List.of(
            "Main Stage",
            "Techno Arena",
            "House Tent",
            "Food Court",
            "VIP Area"
    );

    private final List<String> incidentTypes = List.of(
            "FIGHT",
            "MEDICAL",
            "THEFT",
            "UNAUTHORIZED_ACCESS",
            "LOST_PERSON"
    );

    public SecurityGeneratorService(
            KafkaTemplate<String, SecurityEvent> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 3000)
    public void generateSecurityEvent() {

        SecurityEvent event = new SecurityEvent(
                zones.get(random.nextInt(zones.size())),
                incidentTypes.get(random.nextInt(incidentTypes.size())),
                random.nextInt(5) + 1,
                LocalDateTime.now()
        );

        kafkaTemplate.send(
                "security-events",
                event.zone(),
                event
        );

        System.out.println(
                "Sent security event -> " + event
        );
    }
}