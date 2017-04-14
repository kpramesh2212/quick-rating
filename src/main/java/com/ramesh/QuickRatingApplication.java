package com.ramesh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@ImportResource(value = "classpath:hsql_cfg.xml")
public class QuickRatingApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickRatingApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			public void addCorsMappings(CorsRegistry corsRegistry) {
				corsRegistry.addMapping("/**").allowedOrigins("*")
						.allowedMethods("POST", "GET", "DELETE", "PUT");
			}
		};
	}
}
