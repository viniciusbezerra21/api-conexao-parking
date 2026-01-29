package conexao_parking.api.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                        .title("Conexão Parking API")
                        .description("API Rest da aplicação Conexão Parking, contendo as funcionalidades de CRUD de veículos e proprietários, além de controle de acesso ao estacionamento")
                        .contact(new Contact()
                                .name("Vinicius Bezerra - TI")
                                .email("Vinicius.Bezerra@cmaritima.com.br"))
                        .license(new License()
                                .name("Conexão Parking License")
                                .url("http://conexaoparking.cm/licitacao")));
    }
}
