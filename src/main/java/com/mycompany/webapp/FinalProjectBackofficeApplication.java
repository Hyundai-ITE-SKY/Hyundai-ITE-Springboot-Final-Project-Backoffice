package com.mycompany.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FinalProjectBackofficeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalProjectBackofficeApplication.class, args);
	}

}
