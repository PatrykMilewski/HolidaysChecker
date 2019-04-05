package com.patrykmilewski.bluestone.holidayapi;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
class ApiConfiguration {
    
    @Value("${holidayapi.address}")
    private String holidayApiAddress;
    
    @Value("${holidayapi.key}")
    private String holidayApiKey;
    
}
