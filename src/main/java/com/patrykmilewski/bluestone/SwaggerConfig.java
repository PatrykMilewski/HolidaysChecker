package com.patrykmilewski.bluestone;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
class SwaggerConfig {
    
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .paths(Predicates.not(PathSelectors.regex("/error"))) // Exclude Spring error controllers
                .build();
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "HolidaysChecker API",
                "API for searching first common holidays for given countries.",
                "1.0",
                "Empty",
                new Contact("Patryk Milewski", "github.com/PatrykMilewski", "patryk.milewski@gmail.com"),
                "Empty", "Empty", Collections.emptyList());
    }
}
