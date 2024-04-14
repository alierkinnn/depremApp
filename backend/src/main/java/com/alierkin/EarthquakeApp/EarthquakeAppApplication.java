package com.alierkin.EarthquakeApp;

import com.alierkin.EarthquakeApp.Services.EarthquakeDataProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EarthquakeAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(EarthquakeAppApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(EarthquakeDataProcessor processor) {
		return args -> {
			processor.processEarthquakeData();
		};
	}

}
