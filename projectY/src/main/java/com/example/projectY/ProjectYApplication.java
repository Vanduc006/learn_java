package com.example.projectY;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.example.projectY")
@EnableScheduling
// @CrossOrigin("*")
@EnableAsync
public class ProjectYApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectYApplication.class, args);
	}

}
