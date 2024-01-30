package com.ssafy.tosi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info=@Info(title="등장인물과 대화 API",
        description="동화속에 나오는 등장인물과 대화해보고 싶지 않으신가요?",
        version="v1")
)

@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi chatOpenAPI() {
        String[] paths = {"/gptapi/**"};


        return GroupedOpenApi.builder()
                .group("GPT API V1")
                .pathsToMatch(paths)
                .build();
    }
}
