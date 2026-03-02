package com.lucera.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LuceraBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(LuceraBackendApplication.class, args);
    }
}

