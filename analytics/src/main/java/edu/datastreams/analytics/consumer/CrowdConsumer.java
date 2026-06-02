package edu.datastreams.analytics.consumer;

import edu.datastreams.analytics.model.CrowdDensityEvent;
import edu.datastreams.analytics.query.OverCrowdingDetectionService;
import edu.datastreams.analytics.query.HighRiskAreaDetectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrowdConsumer {

    private final OverCrowdingDetectionService overcrowdingService;
    private final HighRiskAreaDetectionService highRiskService;

    @KafkaListener(
            topics = "crowd-density",
            groupId = "analytics-group",
            containerFactory = "crowdKafkaListenerFactory"
    )
    public void consume(CrowdDensityEvent event) {

        overcrowdingService.process(event);

        highRiskService.processCrowd(event);
    }
}