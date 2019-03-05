package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controllers"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(headerParams());
    }

    private List<Parameter> headerParams() {
        List<Parameter> list =  new ArrayList<Parameter>();
        Parameter param = new ParameterBuilder()
                .name("Authorization")
                .description("Authorization Token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .build();
        list.add(param);
        return list;
    }
}