package com.emailtosms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ReadEmailToSmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReadEmailToSmsApplication.class, args);
	}

}
