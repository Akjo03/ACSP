package com.akjostudios.acsp.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
@RequiredArgsConstructor
@Getter
@Slf4j
public class AcspAuthApp {
    public static void main(String[] args) {
        SpringApplication.run(AcspAuthApp.class, args);
    }
}