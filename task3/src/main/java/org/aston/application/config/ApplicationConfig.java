package org.aston.application.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.aston.application.util.Constants;
import org.aston.application.util.Key;
import org.hibernate.cfg.JdbcSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "org.aston.application")
@PropertySource("classpath:application.properties")
@EnableJpaRepositories(basePackages = "org.aston.application.repository")
public class ApplicationConfig {

    private final Environment env;

    public ApplicationConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(env.getProperty(Key.DB_DRIVER));
        dataSource.setJdbcUrl(env.getProperty(Key.DB_URL));
        dataSource.setUsername(env.getProperty(Key.DB_USER));
        dataSource.setPassword(env.getProperty(Key.DB_PASSWORD));
        return dataSource;
    }

    @Bean
    public DatabaseValidator dataBaseValidator() {
        DatabaseValidator dataBaseValidator = new DatabaseValidator();
        dataBaseValidator.runLiquibase(
                env.getProperty(Key.DB_URL),
                env.getProperty(Key.DB_USER),
                env.getProperty(Key.DB_PASSWORD)
        );
        return dataBaseValidator;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setPackagesToScan("org.aston.application.entity");
        entityManagerFactory.setJpaProperties(hibernateProperties());

        return entityManagerFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty(JdbcSettings.DIALECT, Constants.DB_DIALECT);
        properties.setProperty(JdbcSettings.SHOW_SQL, "true");
        properties.setProperty(JdbcSettings.FORMAT_SQL, "true");
        return properties;
    }
}
