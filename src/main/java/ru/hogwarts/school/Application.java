package ru.hogwarts.school;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition //annotation for connecting Swagger to the project
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
