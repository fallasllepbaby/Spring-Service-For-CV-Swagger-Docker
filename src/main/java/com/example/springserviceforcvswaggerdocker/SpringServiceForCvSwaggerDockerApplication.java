package com.example.springserviceforcvswaggerdocker;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringServiceForCvSwaggerDockerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringServiceForCvSwaggerDockerApplication.class, args);
	}

	@Bean
	public GroupedOpenApi cvService(@Value("1.7.0") String appVersion) {
		return GroupedOpenApi.builder().group("cv-service")
				.addOpenApiCustomiser(openApi -> openApi.info(new Info().title("CV-service API").version(appVersion)))
				.packagesToScan("com")
				.build();
	}

}
