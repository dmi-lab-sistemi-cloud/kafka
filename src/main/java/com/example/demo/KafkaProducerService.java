package com.example.demo;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Profile("producer")
public class KafkaProducerService {
	private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Value("${topic.name}")
	private String topicName;

	public void send(String msg) {
		kafkaTemplate.send(topicName, msg);
		logger.info(msg);
	}

	// approfondimento: ottenere informazioni sull'invio (in maniera sincrona)
	public void sendWithInfoSync(String msg) {
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, msg);
		try {
			SendResult<String, String> result = future.get(); // blocking
			logger.info(String.format("Sent message=[%s] with offset=[%d]", 
				msg, result.getRecordMetadata().offset()));

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	// approfondimento: ottenere informazioni sull'invio (in maniera asincrona)
	public void sendWithInfoAsync(String msg) {
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, msg);
	
    	future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
        	@Override
        	public void onSuccess(SendResult<String, String> result) {
				logger.info(String.format("Sent message=[%s] with offset=[%d]", 
					msg, result.getRecordMetadata().offset()));
        	}
        	@Override
        	public void onFailure(Throwable ex) {
            	logger.error(String.format("Unable to send message=[%s] due to: [%s]", 
            		msg, ex.getMessage()));
        	}
		}); // non-blocking
	}

}