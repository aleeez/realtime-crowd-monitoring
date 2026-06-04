package edu.datastreams.analytics.query;

import edu.datastreams.analytics.dto.DashboardCheckinsDto;
import edu.datastreams.analytics.model.WristbandEvent;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PeakEntryTrafficService {

    private final SimpMessagingTemplate messagingTemplate;

    public PeakEntryTrafficService(
            SimpMessagingTemplate messagingTemplate
    ) {
        this.messagingTemplate = messagingTemplate;
    }

    private final AtomicInteger currentMinuteEntries =
            new AtomicInteger();

    private LocalDateTime minuteStart =
            LocalDateTime.now();

    private int peakEntries = 0;

    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("HH:mm");

    public synchronized void process(
            WristbandEvent event
    ) {

        if (!"ENTRY".equals(event.action())) {
            return;
        }

        currentMinuteEntries.incrementAndGet();

        if (minuteStart.plusMinutes(1)
                .isBefore(LocalDateTime.now())) {

            int currentBurst =
                    currentMinuteEntries.get();

            boolean newRecord = false;

            if (currentBurst > peakEntries) {

                peakEntries = currentBurst;

                newRecord = true;

                System.out.println(
                        "[PEAK TRAFFIC] New peak = "
                                + peakEntries
                                + " entries/minute"
                );
            }

            DashboardCheckinsDto dto =
                    new DashboardCheckinsDto(
                            minuteStart.format(formatter),
                            currentBurst,
                            newRecord
                    );

            messagingTemplate.convertAndSend(
                    "/topic/checkins",
                    dto
            );

            System.out.println(
                    "[WEBSOCKET] Sent checkin update -> "
                            + dto
            );

            currentMinuteEntries.set(0);

            minuteStart = LocalDateTime.now();
        }
    }
}