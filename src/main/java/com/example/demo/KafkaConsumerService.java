package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.kafka.support.KafkaHeaders;

@Service
@Profile("consumer")
public class KafkaConsumerService {
	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

	@KafkaListener(topics = "${topic.name}", groupId = "${group.id}")
	public void listenWithHeaders(
			@Payload String message,
			@Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
		logger.info("Received Message in group foo: " + message + " from partition: " + partition);
	}

}