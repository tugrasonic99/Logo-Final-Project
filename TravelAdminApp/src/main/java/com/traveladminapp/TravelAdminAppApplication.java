package com.traveladminapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class TravelAdminAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelAdminAppApplication.class, args);
	}

}
