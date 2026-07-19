package cl.duoc.ms_clientes_bff.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestión de Clientes")
                        .version("1.0.0")
                        .description("BFF (Backend For Frontend) para gestionar clientes del Hotel de Gatos")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo")
                                .email("dev@hotel-gatos.cl")));
    }
}
