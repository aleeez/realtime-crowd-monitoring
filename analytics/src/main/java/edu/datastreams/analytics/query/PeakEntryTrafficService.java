package edu.datastreams.analytics.query;

import edu.datastreams.analytics.model.WristbandEvent;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PeakEntryTrafficService {

    private final AtomicInteger currentMinuteEntries =
            new AtomicInteger();

    private LocalDateTime minuteStart =
            LocalDateTime.now();

    private int peakEntries = 0;

    public synchronized void process(WristbandEvent event) {

        if (!"ENTRY".equals(event.zone())) {
            return;
        }

        currentMinuteEntries.incrementAndGet();

        if (minuteStart.plusMinutes(1).isBefore(LocalDateTime.now())) {

            int current =
                    currentMinuteEntries.get();

            if (current > peakEntries) {

                peakEntries = current;

                System.out.println(
                        "[PEAK TRAFFIC] New peak = "
                                + peakEntries
                                + " entries/minute"
                );
            }

            currentMinuteEntries.set(0);

            minuteStart = LocalDateTime.now();
        }
    }
}