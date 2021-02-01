package be.fooda.backend.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final String BASIC_AUTH = "basicAuth";
    private static final String BEARER_AUTH = "Bearer";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.basePackage("be.fooda.backend.auth")).paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(securitySchemes())
                .securityContexts(Collections.singletonList(securityContext()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("Fooda Authorization API", "Provides authentication for users.", "1.0", "Terms of service",
                new Contact("Yilmaz Mustafa", "yilmazchef.github.io", "yilmaz@mail.be"), "Free licence", "https://www.apache.org/licenses/", Collections.emptyList());
    }

    private List<SecurityScheme> securitySchemes() {
        return Arrays.asList(new BasicAuth(BASIC_AUTH), new ApiKey(BEARER_AUTH, "Authorization", "header"));
    }

    private SecurityContext securityContext() {
        return SecurityContext
                .builder()
                .securityReferences(Arrays.asList(basicAuthReference(), bearerAuthReference()))
                .forPaths(PathSelectors.ant("/module_name/**")).build();
    }

    private SecurityReference basicAuthReference() {
        return new SecurityReference(BASIC_AUTH, new AuthorizationScope[0]);
    }

    private SecurityReference bearerAuthReference() {
        return new SecurityReference(BEARER_AUTH, new AuthorizationScope[0]);
    }
}