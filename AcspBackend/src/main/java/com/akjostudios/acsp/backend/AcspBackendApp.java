package com.akjostudios.acsp.backend;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@ConfigurationPropertiesScan
@RequiredArgsConstructor
@Getter
@Slf4j
public class AcspBackendApp {
    private final ApplicationContext context;
    private final ApplicationArguments args;

    public static void main(String[] args) {
        SpringApplication.run(AcspBackendApp.class, args);
    }
}