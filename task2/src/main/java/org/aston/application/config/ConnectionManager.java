package org.aston.application.config;

import org.aston.application.exception.DbConnectionException;
import org.aston.application.util.NanoSpring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    static final ApplicationProperties applicationProperties = NanoSpring.find(ApplicationProperties.class);

    static {
        try {
            Class.forName(applicationProperties.getProperty(ApplicationProperties.DATABASE_DRIVER));
        } catch (ClassNotFoundException e) {
            throw new DbConnectionException(ApplicationProperties.DATABASE_DRIVER + "not found", e);
        }
    }

    public Connection get() {
        try {
            return DriverManager.getConnection(
                    applicationProperties.getProperty(ApplicationProperties.DATABASE_URL),
                    applicationProperties.getProperty(ApplicationProperties.DATABASE_USER),
                    applicationProperties.getProperty(ApplicationProperties.DATABASE_PASSWORD)
            );
        } catch (SQLException e) {
            throw new DbConnectionException("connection failed", e);
        }
    }
}
