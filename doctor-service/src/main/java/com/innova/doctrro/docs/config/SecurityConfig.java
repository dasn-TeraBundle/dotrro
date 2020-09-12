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

    private final UserServiceClient userServiceClient;

    @Autowired
    public SecurityConfig(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @Bean
    public SecurityWebFilterChain pringSecurityFilterChain(ServerHttpSecurity http) throws Exception {
        http
                .authorizeExchange()
                .pathMatchers("/doctor-service/doctors/reg-id/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/doctor-service/doctors/").permitAll()
                .pathMatchers("/doctor-service/doc-ratings/doc/**").permitAll()
                .pathMatchers("/doctor-service/doc-ratings/doc-avg/**").permitAll()
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
