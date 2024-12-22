package com.LetMeDoWith.LetMeDoWith.presentation.config;

import io.swagger.v3.core.util.OpenAPI30To31;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI() {
    SecurityScheme securityScheme = new SecurityScheme().type(Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT")
        .in(In.HEADER)
        .name("Authorization");

    SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

    return new OpenAPI().components(
        new Components().addSecuritySchemes("bearerAuth", securityScheme)).security(Arrays.asList(securityRequirement));

  }

}
