package org.aston.application;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

@Slf4j
public class TestContainer {

    @Container
    private static final PostgreSQLContainer<?> CONTAINER;
    private static final String DOCKER_IMAGE_NAME = "postgres:16.3";

    static {
        CONTAINER = new PostgreSQLContainer<>(DOCKER_IMAGE_NAME);
        CONTAINER.start();
    }

    public TestContainer() {
        log.info("Starting container...");
    }

}
