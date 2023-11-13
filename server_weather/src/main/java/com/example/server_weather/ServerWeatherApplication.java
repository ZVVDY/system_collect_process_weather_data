package com.example.server_weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories("com.example.server_weather.repository")
@EntityScan("com.example.server_weather.model.entity")
@EnableScheduling
public class ServerWeatherApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerWeatherApplication.class, args);
	}

}
