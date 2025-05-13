package com.github.dmkyr20.climb2n;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class Climb2nApplication {

    public static void main(String[] args) {
        SpringApplication.run(Climb2nApplication.class, args);
    }

}
