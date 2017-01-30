package com.ramesh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(value = "classpath:hsql_cfg.xml")
public class QuickRatingApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickRatingApplication.class, args);
	}
}
