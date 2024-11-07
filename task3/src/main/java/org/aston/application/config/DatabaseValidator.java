package org.aston.application.config;

import liquibase.Scope;
import liquibase.command.CommandScope;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.extern.slf4j.Slf4j;
import org.aston.application.exception.AppException;
import org.aston.application.util.Constants;
import org.aston.application.util.Key;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DatabaseValidator {

    public void runLiquibase(String url, String username, String password) {
        log.info("Liquibase validation started...");
        try {
            Scope.child(Scope.Attr.resourceAccessor, new ClassLoaderResourceAccessor(), () -> {
                CommandScope update = new CommandScope("update");
                update.addArgumentValue(Key.CHANGELOG_FILE, Constants.DB_CHANGELOG_XML);
                update.addArgumentValue(Key.URL, url);
                update.addArgumentValue(Key.USERNAME, username);
                update.addArgumentValue(Key.PASSWORD, password);
                update.execute();
            });
        } catch (Exception e) {
            throw new AppException("DataBaseValidator Liquibase validation failed. Creating child scope failed", e);
        }
        log.info("Liquibase validation has been successfully completed.");
    }
}






