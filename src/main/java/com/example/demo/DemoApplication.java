package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
	
	//To view api doc via swagger goto below link
	// http://localhost:8080/swagger-ui.html
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
