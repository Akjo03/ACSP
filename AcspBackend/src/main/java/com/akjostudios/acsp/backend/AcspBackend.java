package com.akjostudios.acsp.backend;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableReactiveMongoRepositories
@RequiredArgsConstructor
@Getter
@Log4j2
public class AcspBackend {
    private final ApplicationContext context;
    private final ApplicationArguments args;

    public static void main(String[] args) {
        SpringApplication.run(AcspBackend.class, args);
    }
}