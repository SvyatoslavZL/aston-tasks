package org.aston.application.config;

import liquibase.Scope;
import liquibase.command.CommandScope;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.aston.application.exception.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataBaseValidator {

    public static final String DB_CHANGELOG_XML = "db/changelog/changelog.xml";
    private static final Logger log = LoggerFactory.getLogger(DataBaseValidator.class);
    private final ApplicationProperties properties;

    public DataBaseValidator(ApplicationProperties properties) {
        this.properties = properties;
    }

    public void runLiquibase() {
        log.info("Liquibase validation started...");

        try {
            Scope.child(Scope.Attr.resourceAccessor, new ClassLoaderResourceAccessor(), () -> {
                CommandScope update = new CommandScope("update");
                update.addArgumentValue("changelogFile", DB_CHANGELOG_XML);
                String url = properties.getProperty(ApplicationProperties.DATABASE_URL);
                update.addArgumentValue("url", url);
                String username = properties.getProperty(ApplicationProperties.DATABASE_USER);
                update.addArgumentValue("username", username);
                String password = properties.getProperty(ApplicationProperties.DATABASE_PASSWORD);
                update.addArgumentValue("password", password);
                update.execute();
            });
        } catch (Exception e) {
            throw new AppException("DataBaseValidator Liquibase validation failed. Creating child scope failed", e);
        }

        log.info("Liquibase validation has been successfully completed.");
    }

}






