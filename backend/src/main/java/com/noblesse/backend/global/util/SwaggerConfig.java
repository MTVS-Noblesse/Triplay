package com.noblesse.backend.global.util;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Triplay API Document",
                description = "Triplay API 명세서",
                version = "1.0.0"
        ),
        servers = {
                @Server(url = "https://localhost:8443", description = "Local Server"),
                @Server(url = "https://triplay.com", description = "Production Server")
        }
)
public class SwaggerConfig {
}
