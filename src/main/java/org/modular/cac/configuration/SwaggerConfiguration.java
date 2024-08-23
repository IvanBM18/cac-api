package org.modular.cac.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public GroupedOpenApi openAPI(){
        return GroupedOpenApi.builder()
                .group("Cac Endpoints")
                .packagesToScan("org.modular.cac")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
            .info(new Info().title("Cac API").version("0.2").description("UI for testing CAC's DB API"));
    }
}
