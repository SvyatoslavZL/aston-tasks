package org.aston.application;

import org.aston.application.config.ApplicationProperties;
import org.aston.application.config.Configuration;
import org.aston.application.util.NanoSpring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

public class TestContainer {

    @Container
    private static final JdbcDatabaseContainer<?> CONTAINER;

    private static final String DOCKER_IMAGE_NAME = "postgres:16.3";
    private static final Logger log = LoggerFactory.getLogger(TestContainer.class);

    static {
        CONTAINER = new PostgreSQLContainer<>(DOCKER_IMAGE_NAME);
        CONTAINER.start();

        ApplicationProperties properties = NanoSpring.find(ApplicationProperties.class);
        properties.setProperty(ApplicationProperties.DATABASE_URL, CONTAINER.getJdbcUrl());
        properties.setProperty(ApplicationProperties.DATABASE_USER, CONTAINER.getUsername());
        properties.setProperty(ApplicationProperties.DATABASE_PASSWORD, CONTAINER.getPassword());

        Configuration configuration = NanoSpring.find(Configuration.class);
        configuration.fillInDatabase();
    }

    public TestContainer() {
        log.info("Starting container...");
    }

}
