package com.test.example.api.rest.docs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
@ComponentScan("com.test.example.api.rest")
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("public-api")
                .select()
//                .apis(RequestHandlerSelectors.any())
//                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
//                .paths(Predicates.not(PathSelectors.regex("/error")))
                .apis(RequestHandlerSelectors.basePackage("org.springframework.boot").negate())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        String description = "REST example";
        return new ApiInfoBuilder()
                .title("REST example")
                .description(description)
                .termsOfServiceUrl("github")
                .license("Apache 2.0")
                .licenseUrl("")
                .version("1.0")
                .build();
    }
}
