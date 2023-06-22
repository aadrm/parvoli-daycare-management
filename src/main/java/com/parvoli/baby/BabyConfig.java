package com.parvoli.baby;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;

@Configuration
public class BabyConfig {

    @Bean
    CommandLineRunner commandLineRunner(BabyRepository repository) {
       return args -> {
           Baby iggy = new Baby(
                   "Iggy",
                   "Adrian",
                   LocalDate.of(2023, Month.APRIL, 12)
           );
       };
    }
}
