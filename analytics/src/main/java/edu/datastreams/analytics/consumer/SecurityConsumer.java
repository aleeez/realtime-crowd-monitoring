package edu.datastreams.analytics.consumer;

import edu.datastreams.analytics.model.SecurityEvent;
import edu.datastreams.analytics.query.HighRiskAreaDetectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityConsumer {

    private final HighRiskAreaDetectionService highRiskService;

    @KafkaListener(
            topics = "security-events",
            groupId = "analytics-group",
            containerFactory = "securityKafkaListenerFactory"
    )
    public void consume(SecurityEvent event) {

        highRiskService.processSecurity(event);
    }
}