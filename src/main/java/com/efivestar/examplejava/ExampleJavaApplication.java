package com.efivestar.examplejava;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author scottyan
 */
@SpringBootApplication
@Slf4j
@EnableJpaAuditing
public class ExampleJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleJavaApplication.class, args);
    }

}
