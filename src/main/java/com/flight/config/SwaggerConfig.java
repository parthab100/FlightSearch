package com.flight.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Collections;

@Configuration
/**
 * Configuration class to access Swagger UI
 */
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.flight.controller")).paths(PathSelectors.any()).build()
				.pathMapping("/flightFinder/flights");
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("Flight Search Rest APIs", "APIs for Flight Search.", "1.0", "Terms of service",
				new Contact("test", "", "test@emaildomain.com"), "License of API", "API license URL",
				Collections.emptyList());
	}

}
