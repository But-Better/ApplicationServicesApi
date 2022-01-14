package com.butbetter.applicationservices;

import com.butbetter.applicationservices.csvExporter.properties.CSVExporterProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(CSVExporterProperties.class)
public class ApplicationServices {
	public static void main(String[] args) {
		SpringApplication.run(ApplicationServices.class, args);
	}
}
