package com.patrykmilewski.bluestone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
class TestController {
    
    @Value("${spring.profiles.active}")
    private String profileName;
    
    @GetMapping("hello")
    public String hello() {
        return "Hello world from " + profileName + " profile!";
    }
    
    @GetMapping("yo")
    public String yo() {
        return "Hello world from " + profileName + " profile!";
    }
    
}
