package edu.datastreams.security_generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SecurityGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityGeneratorApplication.class, args);
	}

}
