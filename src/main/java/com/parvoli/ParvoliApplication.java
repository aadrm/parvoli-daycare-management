package com.parvoli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.time.Clock;

@SpringBootApplication
@ComponentScan(basePackages = "com.parvoli")
public class ParvoliApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParvoliApplication.class, args);
	}

}
