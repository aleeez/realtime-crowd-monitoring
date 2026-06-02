package edu.datastreams.analytics.consumer;

import edu.datastreams.analytics.model.WristbandEvent;
import edu.datastreams.analytics.query.PeakEntryTrafficService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WristbandConsumer {

    private final PeakEntryTrafficService peakEntryTrafficService;

    @KafkaListener(
            topics = "wristband-events",
            groupId = "analytics-group",
            containerFactory = "wristbandKafkaListenerFactory"
    )
    public void consume(WristbandEvent event) {

        peakEntryTrafficService.process(event);
    }
}