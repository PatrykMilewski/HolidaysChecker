package com.patrykmilewski.bluestone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class HolidaysChecker {
    
    public static void main(String[] args) {
        SpringApplication.run(HolidaysChecker.class, args);
    }
    
}
