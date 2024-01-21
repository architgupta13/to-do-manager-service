package org.archit.todomanagerservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Collections;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI api() {
        val devServer = new Server();
        devServer.setUrl("http://localhost:8090");
        devServer.setDescription("This is a development environment");

        val contact = new Contact();
        contact.setEmail("gupta.archit13@gmail.com");
        contact.setName("Archit Gupta");
        contact.setUrl("https://www.github.com/architgupta13");

        val mitLicense = new License().name("MIT License")
            .url("https://choosealicense.com/licenses/mit/");

        val info = new Info()
            .title("TO-DO Manager API")
            .version("1.0")
            .contact(contact)
            .description("This API exposes endpoints to manage to-do lists.")
            .license(mitLicense);

        return new OpenAPI().info(info).servers(Collections.singletonList(devServer));

    }

}