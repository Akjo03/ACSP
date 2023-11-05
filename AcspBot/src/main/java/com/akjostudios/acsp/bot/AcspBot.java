package com.akjostudios.acsp.bot;

import com.akjostudios.acsp.bot.properties.ConfigProperties;
import com.akjostudios.acsp.bot.util.ExecutionCode;
import io.github.akjo03.lib.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Objects;

@SpringBootApplication
@ConfigurationPropertiesScan
@RequiredArgsConstructor
@Log4j2
public class AcspBot implements ApplicationRunner {
    private final ApplicationContext context;
    private final ApplicationArguments args;
    private final ConfigProperties configProperties;

    public static void main(String[] args) {
        SpringApplication.run(AcspBot.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("---------------------------------------------------------------");

        if (configProperties.getEnvironment() == null) {
            log.error("Environment not set. Please set the environment using a profile.");
            SpringApplication.exit(context, () -> 1);
        }

        log.info("Starting ACSP Discord Bot in {} environment...", configProperties.getEnvironment());

        ExecutionCode exitCode = runBot().getOrElse(ExecutionCode.GENERIC_ERROR);
        if (Objects.equals(exitCode, ExecutionCode.RESTART)) {
            restart(); return;
        }

        log.info("Exiting ACSP Discord Bot with exit code {}.", exitCode);
        log.info("---------------------------------------------------------------");

        SpringApplication.exit(context, exitCode::getCode);
    }

    private @NotNull Result<ExecutionCode> runBot() {
        return Result.success(ExecutionCode.SUCCESS);
    }

    public void restart() {
        Thread restartThread = new Thread(() -> {
            log.info("Restarting ACSP Discord Bot...");
            ConfigurableApplicationContext context = (ConfigurableApplicationContext) this.context;
            context.close();
            SpringApplication.run(AcspBot.class, args.getSourceArgs());
        });
        restartThread.setDaemon(false);
        restartThread.start();
    }
}