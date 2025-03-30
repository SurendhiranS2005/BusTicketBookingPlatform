package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI busManagementSystemOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Bus Management System API")
                        .description("API documentation for the Bus Management System")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("API Support")
                                .email("support@bussystem.com")
                                .url("https://www.bussystem.com/contact"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local Development Server"),
                        new Server()
                                .url("https://api.bussystem.com")
                                .description("Production Server")));
    }
}