package com.previred.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author cristopher
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.previred.controllers")).paths(PathSelectors.any())
				.build().apiInfo(getApiInfo());
	}

	private ApiInfo getApiInfo() {
		return new ApiInfo("Servicio de documentacion de API", "Servicio de documentacion de API con Swagger y OpenApi",
				"1.0", "https://www.previred.com",
				new Contact("Cristopher Reyes", "https://cristopherreyes.com", "cristopheralexanderr@gmail.com"), "MIT",
				"https://www.urlincencia.com", Collections.emptyList());
	}
}
