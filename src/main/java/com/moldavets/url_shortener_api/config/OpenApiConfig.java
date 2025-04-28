package com.moldavets.url_shortener_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(
        info = @Info(
                title = "Url Shortener",
                description = "Url Shortener REST API", version = "1.0.0",
                contact = @Contact(
                        name = "Bohdan Moldavets - steamdlmb@gmail.com",
                        email = "steamdlmb@gmail.com"
                )
        )
)
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addResponses("InternalServerError",
                                new ApiResponse()
                                        .description("Internal Server Error")
                                        .content(new Content().addMediaType("application/json",
                                                new MediaType().schema(new Schema<>().$ref("#/components/schemas/ExceptionDetailsModel"))
                                        ))
                        )
                );
    }
}
