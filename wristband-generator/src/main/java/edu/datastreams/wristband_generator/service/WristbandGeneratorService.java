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

    private final List<String> ticketTypes = List.of(
            "GENERAL_PASS",
            "GENERAL_DAY_TICKET",
            "VIP_PASS",
            "VIP_DAY_TICKET"
    );

    public WristbandGeneratorService(
            KafkaTemplate<String, WristbandEvent> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 5000)
    public void generateWristbandScans() {

        int burstSize =
                random.nextInt(10) + 1;

        System.out.println(
                "[WRISTBAND GENERATOR] Generating "
                        + burstSize
                        + " events..."
        );

        for (int i = 0; i < burstSize; i++) {

            WristbandEvent event =
                    new WristbandEvent(
                            "WB-" + random.nextInt(10000),
                            "ATT-" + UUID.randomUUID()
                                    .toString()
                                    .substring(0, 8),

                            ticketTypes.get(
                                    random.nextInt(
                                            ticketTypes.size()
                                    )
                            ),

                            random.nextBoolean()
                                    ? "ENTRY"
                                    : "EXIT",

                            LocalDateTime.now()
                    );

            kafkaTemplate.send(
                    "wristband-events",
                    event.wristbandId(),
                    event
            );

            System.out.println(
                    "Sent wristband event -> "
                            + event
            );
        }
    }
}