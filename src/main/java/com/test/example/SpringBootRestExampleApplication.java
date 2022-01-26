package com.test.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.test.example.dao.jpa")
@SpringBootApplication
public class SpringBootRestExampleApplication extends SpringBootServletInitializer {

    private static final Logger log = LoggerFactory.getLogger(SpringBootRestExampleApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(SpringBootRestExampleApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootRestExampleApplication.class);
    }
}
