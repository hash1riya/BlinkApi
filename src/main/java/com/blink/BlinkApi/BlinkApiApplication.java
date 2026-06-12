package com.blink.BlinkApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class BlinkApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlinkApiApplication.class, args);
	}

}
