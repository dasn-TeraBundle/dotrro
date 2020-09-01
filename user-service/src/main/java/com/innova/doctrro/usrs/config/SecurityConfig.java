package com.innova.doctrro.usrs.config;
//

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector;
import org.springframework.security.web.server.SecurityWebFilterChain;

//
//@Configuration
//@EnableResourceServer
//public class SecurityConfig extends ResourceServerConfigurerAdapter {
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/me")
//                .authenticated()
//                .antMatchers("/roles/**")
//                .permitAll();
//    }
//}
//
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain pringSecurityFilterChain(ServerHttpSecurity http) throws Exception {
        http
                .authorizeExchange()
                .pathMatchers("/**/roles/**", "/**/**/roles/**").permitAll()
                .anyExchange().authenticated()
                .and()
                .oauth2ResourceServer()
                .opaqueToken();

        return http.build();
    }

    @Bean
    public ReactiveOpaqueTokenIntrospector introspector() {
        return new UserInfoOpaqueTokenIntrospector();
    }
}
