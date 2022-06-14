package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Profile("producer")
@RestController
public class ProducerController {
    @Autowired 
	KafkaProducerService producer;

	@GetMapping("/send/{msg}")
	public String send(@PathVariable String msg) {
		producer.send(msg);
		return "sent msg: " + msg  + "\n";
	}

	@GetMapping("/send-sync/{msg}")
	public String sendSync(@PathVariable String msg) {
		producer.sendWithInfoSync(msg);
		return "sent msg (see docker logs for sync info): " + msg + "\n";
	}

	@GetMapping("/send-async/{msg}")
	public String sendAsync(@PathVariable String msg) {
		producer.sendWithInfoAsync(msg);
		return "sent msg (see docker logs for async info): " + msg  + "\n";
	}
}
