package com.tmt.challenge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.host}")
    private String swaggerHost;

    @Value("${swagger.path}")
    private String swaggerPort;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(operationParameters())
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .host(swaggerHost)
                .pathMapping(swaggerPort)
                .securitySchemes(getSecuritySchemes())
                .securityContexts(getSecurityContexts());
    }

    private List<ApiKey> getSecuritySchemes() {
        return Collections.singletonList(new ApiKey("JWT", "Authorization", "header"));
    }

    private List<SecurityContext> getSecurityContexts() {
        return Collections.singletonList(
                SecurityContext.builder().securityReferences(
                        Collections.singletonList(SecurityReference.builder()
                                .reference("JWT")
                                .scopes(new AuthorizationScope[0])
                                .build()
                        )
                ).build()
        );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Spring Boot API")
                .description("Spring Boot Challenge API reference for developers")
                .termsOfServiceUrl("")
                .license("MIT License")
                .licenseUrl("ridhan.fadhilah@sg-edts.com").version("1.0").build();
    }

    private List<Parameter> operationParameters() {
        List<Parameter> headers = new ArrayList<>();
        headers.add(new ParameterBuilder().name("Accept-Language").description("Accept Language fo i18n").defaultValue("en")
                .modelRef(new ModelRef("string")).parameterType("header").required(false).build());
        return headers;
    }
}
