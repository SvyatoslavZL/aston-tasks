package org.aston.application.config;

import org.aston.application.exception.DbConnectionException;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

public class ApplicationProperties extends Properties {

    public static final String DATABASE_DRIVER = "database.driver";
    public static final String DATABASE_URL = "database.url";
    public static final String DATABASE_USER = "database.user";
    public static final String DATABASE_PASSWORD = "database.password";

    public ApplicationProperties() {
        try (FileReader reader = new FileReader(CLASSES_ROOT + "/application.properties")){
            this.load(reader);
            String driver = this.getProperty(DATABASE_DRIVER);
            Class.forName(driver);
        } catch (ClassNotFoundException | IOException e) {
            throw new DbConnectionException(e);
        }
    }

    public static final Path CLASSES_ROOT = Paths.get(URI.create(
            Objects.requireNonNull(
                    ApplicationProperties.class.getResource("/")
            ).toString()
    ));

}
