package edu.datastreams.crowd_generator.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {
    
    @Bean
    public NewTopic crowdDensityTopic() {

        return TopicBuilder
                .name("crowd-density")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
