package com.example.logging_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(PartnerHealthProperties.class)
public class LoggingDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoggingDemoApplication.class, args);
	}

}
