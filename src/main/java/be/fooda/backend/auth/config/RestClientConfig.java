package be.fooda.backend.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

public class RestClientConfig {

    @Bean
    public RestTemplate getRestTemplateBean() {
        return new RestTemplate();
    }

}
