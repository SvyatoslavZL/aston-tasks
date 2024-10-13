package org.aston.application.config;

public class Configuration {

    private final DataBaseValidator dataBaseValidator;

    public Configuration(DataBaseValidator dataBaseValidator) {
        this.dataBaseValidator = dataBaseValidator;
    }

    public void fillInDatabase() {
        dataBaseValidator.runLiquibase();
    }

}
