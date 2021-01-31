package be.fooda.backend.auth;

import be.fooda.backend.auth.config.RestClientConfig;
import be.fooda.backend.auth.config.SecurityCredentialsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
//@EnableEurekaClient
@EnableWebSecurity
@Import({
        RestClientConfig.class,
        SecurityCredentialsConfig.class
})
public class AuthApp {
    public static void main(String[] args) {
        SpringApplication.run(AuthApp.class, args);
    }
}
