package edu.datastreams.analytics.config;

import edu.datastreams.analytics.model.CrowdDensityEvent;
import edu.datastreams.analytics.model.SecurityEvent;
import edu.datastreams.analytics.model.WristbandEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    private Map<String, Object> consumerProps() {

        Map<String, Object> props = new HashMap<>();

        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers
        );

        props.put(
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                "earliest"
        );

        return props;
    }

    @Bean
    public ConsumerFactory<String, CrowdDensityEvent> crowdConsumerFactory() {

        JsonDeserializer<CrowdDensityEvent> deserializer =
                new JsonDeserializer<>(CrowdDensityEvent.class);

        deserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                consumerProps(),
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConsumerFactory<String, SecurityEvent> securityConsumerFactory() {

        JsonDeserializer<SecurityEvent> deserializer =
                new JsonDeserializer<>(SecurityEvent.class);

        deserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                consumerProps(),
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConsumerFactory<String, WristbandEvent> wristbandConsumerFactory() {

        JsonDeserializer<WristbandEvent> deserializer =
                new JsonDeserializer<>(WristbandEvent.class);

        deserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                consumerProps(),
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CrowdDensityEvent>
    crowdKafkaListenerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, CrowdDensityEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(crowdConsumerFactory());

        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SecurityEvent>
    securityKafkaListenerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, SecurityEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(securityConsumerFactory());

        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, WristbandEvent>
    wristbandKafkaListenerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, WristbandEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(wristbandConsumerFactory());

        return factory;
    }
}