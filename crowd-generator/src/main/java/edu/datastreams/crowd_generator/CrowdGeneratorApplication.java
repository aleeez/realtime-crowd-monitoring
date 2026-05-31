package edu.datastreams.crowd_generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CrowdGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrowdGeneratorApplication.class, args);
	}

}
