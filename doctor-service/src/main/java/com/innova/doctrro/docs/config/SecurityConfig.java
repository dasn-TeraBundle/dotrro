package com.innova.doctrro.docs.config;

import com.innova.doctrro.docs.service.UserServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private static final String ROOT = "/doctor-service";

    private final UserServiceClient userServiceClient;

    @Autowired
    public SecurityConfig(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @Bean
    public SecurityWebFilterChain pringSecurityFilterChain(ServerHttpSecurity http) throws Exception {
        http
                .authorizeExchange()

                .pathMatchers(ROOT + "/**/me").authenticated()

                .pathMatchers(HttpMethod.GET, ROOT + "/doctors/**").permitAll()
                .pathMatchers(HttpMethod.GET, ROOT + "/doctors/").permitAll()

                .pathMatchers(ROOT + "/doc-ratings/**").permitAll()
                .pathMatchers(ROOT + "/doc-ratings/avg/**").permitAll()

                .pathMatchers(HttpMethod.GET,ROOT + "/facility/**").permitAll()
                .pathMatchers(HttpMethod.GET,ROOT + "/facility/").permitAll()
                .anyExchange().authenticated()
                .and()
                .oauth2ResourceServer()
                .opaqueToken();

        return http.build();
    }

    @Bean
    public ReactiveOpaqueTokenIntrospector introspector() {
        return new UserInfoOpaqueTokenIntrospector(userServiceClient);
    }
}
