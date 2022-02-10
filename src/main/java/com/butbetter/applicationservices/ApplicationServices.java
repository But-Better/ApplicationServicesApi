package com.butbetter.applicationservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ApplicationServices {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationServices.class, args);
	}

}
