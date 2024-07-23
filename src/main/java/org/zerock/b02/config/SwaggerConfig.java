package org.zerock.b02.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info=@Info(title="Zerock App", version="v2")
)
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi chatOpenApi(){
        String[] paths = {"/**"};
        return GroupedOpenApi.builder()
                .group("Zerock Open API v2")
                .pathsToMatch(paths)
                .build();
    }

}
