package com.hotwheels.backendtask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BackendtaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendtaskApplication.class, args);
    }
}
