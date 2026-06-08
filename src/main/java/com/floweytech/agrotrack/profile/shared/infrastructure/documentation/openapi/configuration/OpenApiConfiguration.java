package com.floweytech.agrotrack.profile.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {

    // General Info
    @Value("${documentation.application.title}")
    private String applicationTitle;

    @Value("${documentation.application.description}")
    private String applicationDescription;

    @Value("${documentation.application.version}")
    private String applicationVersion;

    @Bean
    public OpenAPI agrotrackPlatformOpenAPI() {

        var openApi = new OpenAPI();
        openApi
                .info(new Info()
                        .title(applicationTitle)
                        .description(applicationDescription)
                        .version(applicationVersion)
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("Agrotrack Platform Documentation")
                        .url("https://agrotrack-platform.wiki.github.io/docs"));

        // Se configuran los servidores para que Swagger sepa a dónde enviar las peticiones.
        // Azure como  Localhost por si se necesita probar de manera local.
        openApi.servers(List.of(
                new Server().url("https://agrotrack-profile-service-fwfjh7evgrb0dmhg.centralus-01.azurewebsites.net").description("Azure Production"),
                new Server().url("http://localhost:8082").description("Local Development")
        ));

        return openApi;
    }
}