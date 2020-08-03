package com.ssafy.waple.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import com.ssafy.waple.bookmark.dto.SearchType;
import com.ssafy.waple.group.controller.GroupController;
import com.ssafy.waple.promise.controller.PromiseController;
import com.ssafy.waple.user.controller.UserController;

//http://localhost:9999/swagger-ui/
@Configuration
public class SwaggerConfig {
	@Autowired
	private TypeResolver resolver;

	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2)
			.groupName("Waple")
			.additionalModels(resolver.resolve(SearchType.class))
			.additionalModels(resolver.resolve(UserController.AccessToken.class))
			.additionalModels(resolver.resolve(UserController.RefreshToken.class))
			.additionalModels(resolver.resolve(GroupController.GroupDtoExample.class))
			.additionalModels(resolver.resolve(GroupController.GroupMemberDtoExample.class))
			.additionalModels(resolver.resolve(PromiseController.PromiseRequest.class))
			.useDefaultResponseMessages(false)
			.apiInfo(apiInfo())
			.select()
			.apis(RequestHandlerSelectors.basePackage("com.ssafy.waple"))
			.paths(PathSelectors.ant("/**"))
			.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("SSAFY API")
			.description("SSAFY API Reference for Developers")
			.termsOfServiceUrl("https://edu.ssafy.com")
			.license("SSAFY License")
			.licenseUrl("ssafy@ssafy.com").version("1.0").build();
	}
}
