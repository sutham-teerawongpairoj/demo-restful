package com.biztech.demo.util;

import org.hibernate.validator.internal.util.Contracts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket selectAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
//                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.biztech.demo.apicontroller"))
//                .paths(PathSelectors.any())
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .apiInfo(this.apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "DEMO RESTFUL SERVICE",
                "DEMO RESTFUL SERVICE <br> - SPINGBOOT 2.0.1<br> - GRADLE<br> - MYSQL 5.1.36<br> - GSON 2.7<br> - SPRINGFOX-SWAGGER2 2.7.0<br> - SPRINGFOX-SWAGGER-UI 2.7.0",
                "VERSION 0",
                "TERMS OF SERVICE URL",
                new Contact("Sutham Teerawongpairoj","","sutham.teerawongpairoj@krungsri.com"),
                "LICENSE",
                "LICENSE URL",
                Collections.emptyList()
        );
    }
}
