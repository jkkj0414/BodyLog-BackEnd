package com.solutionchallenge.bodylog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BodylogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BodylogApplication.class, args);
    }

}