package com.demo.blogapp.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
   
	public static final String AUTHORIZATION_HEADER = "Authorization";
	
	private ApiKey apiKeys() {
	
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");	
	}
	
	private List<SecurityContext> securityContext(){
		return Arrays.asList(SecurityContext.builder().securityReferences(sf()).build());
		
	}
	private List<SecurityReference> sf(){
		AuthorizationScope scope = new AuthorizationScope("global","Access everything");
		return Arrays.asList(new SecurityReference("jwt", new AuthorizationScope[] {scope} ));
		
	}
	
//	builder which is intended to be the primary interface into the Springfox framework.Provides sensible defaults and convenience methods for configuration.
	@Bean
	public Docket api() {
		
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getInfo())
				.securityContexts(securityContext())
				.securitySchemes(Arrays.asList(apiKeys()))
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo getInfo() {
		
		return new ApiInfo("BlogApplication - REST API", "This is a project which is implemeting all the best practises", "1.0", "Terms of Service", new Contact("Abhishek Ikhar", "https://github.com/AbhiIkhar", "ikharabhishek177@gmail.com"), "Licence of Urls", "Url", Collections.emptyList());
	}
}