package com.HPE.Forage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
// @ComponentScan(basePackages = {"com.HPE.Forage.Model"})
public class ForageApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForageApplication.class, args);
	}

}
