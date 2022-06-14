package com.example.demo.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@Profile("producer")
public class KafkaProducerConfig {
	@Value("${kafka.bootstrapAddress}")
    private String bootstrapAddress;
 
	/**
	 * Per dettagli sulle opzioni di configurazione:
	 * http://kafka.apache.org/documentation.html#producerconfigs
	 * 
	 * @return
	 */
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> config = new HashMap<>();
		
		/*
		 * A list of host/port pairs to use for establishing 
		 * the initial connection to the Kafka cluster
		 */
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		
		/**
		 * Serializer class for key that implements the 
		 * org.apache.kafka.common.serialization.Serializer interface.
		 */
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		
		/**
		 * Serializer class for value that implements the 
		 * org.apache.kafka.common.serialization.Serializer interface.
		 */
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		
		return new DefaultKafkaProducerFactory<>(config);
    }
 
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}