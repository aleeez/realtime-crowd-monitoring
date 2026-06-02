package edu.datastreams.security_generator.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {

    @Bean
    public NewTopic securityTopic() {
        return new NewTopic(
                "security-events",
                1,
                (short) 1
        );
    }
}