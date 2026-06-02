package edu.datastreams.analytics.query;

import edu.datastreams.analytics.model.CrowdDensityEvent;
import edu.datastreams.analytics.model.SecurityEvent;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class HighRiskAreaDetectionService {

    private final Map<String, Integer> crowdLevels =
            new ConcurrentHashMap<>();

    private final Map<String, Integer> securityLevels =
            new ConcurrentHashMap<>();

    public void processCrowd(CrowdDensityEvent event) {

        crowdLevels.put(
                event.stage(),
                event.peopleCount()
        );

        evaluate(event.stage());
    }

    public void processSecurity(SecurityEvent event) {

        securityLevels.put(
                event.zone(),
                event.severity()
        );

        evaluate(event.zone());
    }

    private void evaluate(String zone) {

        Integer crowd =
                crowdLevels.getOrDefault(zone, 0);

        Integer security =
                securityLevels.getOrDefault(zone, 0);

        if (crowd > 1000 && security >= 4) {

            System.out.println(
                    "[HIGH RISK AREA] " +
                    zone +
                    " crowd=" +
                    crowd +
                    " severity=" +
                    security
            );
        }
    }
}