package be.fooda.backend.auth.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @LoadBalanced
    @Bean
    public RestTemplate getLoadBalancedRestTemplate() {
        return new RestTemplate();
    }

    @Primary
    @Bean
    public RestTemplate getDefaultRestTemplate() {
        return new RestTemplate();
    }

}
