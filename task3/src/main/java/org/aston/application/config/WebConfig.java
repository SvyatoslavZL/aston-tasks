package org.aston.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan("org.aston.application")
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public OpenSessionInViewFilter openSessionInViewFilter() {
        return new OpenSessionInViewFilter();
    }

}