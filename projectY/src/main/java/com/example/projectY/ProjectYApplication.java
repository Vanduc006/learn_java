package com.example.projectY;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.projectY")
// @CrossOrigin("*")
public class ProjectYApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectYApplication.class, args);
	}

}
