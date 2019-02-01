package com.example.homesol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HomeSolApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeSolApplication.class, args);
	}

	// To run from the command line 
	// mvn spring-boot:run
	
}

