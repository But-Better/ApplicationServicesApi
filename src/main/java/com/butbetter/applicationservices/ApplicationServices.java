package com.butbetter.applicationservices;

import org.hibernate.cfg.Environment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class ApplicationServices {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationServices.class, args);
	}

}
