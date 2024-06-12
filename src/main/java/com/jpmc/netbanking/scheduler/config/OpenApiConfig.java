package com.jpmc.netbanking.scheduler.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
info = @Info(
        title = "Scheduler api",
        description = "Application for scheduler",
        version = "v1"
)
)
@Configuration
public class OpenApiConfig {

}
