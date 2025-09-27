package com.tamara.BankingAppSpringBoot;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info (
				title = "Spring Boot Banking System",
				description = "This is the API documentation for Tamara's Backend Banking App built with Spring Boot.",
				version = "1.0.0",
				contact =  @Contact(
						name = "Tamara Ghosn",
						email = "tamaraghosn01@gmail.com",
						url ="https://github.com/tamaraghosn/springboot-banking-system"
				),
				license = @License(
						name = "Tamara Ghosn",
						url ="https://github.com/tamaraghosn"
				)

		),
		externalDocs = @ExternalDocumentation(
				description = "Find more details in the project repository",
				url = "https://github.com/tamaraghosn/springboot-banking-system"

		)
)
public class BankingAppSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingAppSpringBootApplication.class, args);
	}

}
