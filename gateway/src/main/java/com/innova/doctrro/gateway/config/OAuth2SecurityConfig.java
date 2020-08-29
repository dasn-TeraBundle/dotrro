package com.innova.doctrro.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcReactiveOAuth2UserService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.ReactiveOAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.HashSet;
import java.util.Set;

@EnableWebFluxSecurity
public class OAuth2SecurityConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2SecurityConfig.class);

    @Bean
    public SecurityWebFilterChain pringSecurityFilterChain(ServerHttpSecurity http) throws Exception {
        http
                .authorizeExchange()
                .anyExchange().authenticated()
                .and()
                .oauth2Login()
        ;

        return http.build();
    }

    @Bean
    public ReactiveOAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        final var delegate = new OidcReactiveOAuth2UserService();

        return (userRequest) -> {
            // Delegate to the default implementation for loading a user
            return delegate.loadUser(userRequest)
                    .map(oidcUser1 -> {
                        OAuth2AccessToken accessToken = userRequest.getAccessToken();
                        Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
                        mappedAuthorities.addAll(oidcUser1.getAuthorities());
                        mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_PATIENT"));

                        // TODO
                        // 1) Fetch the authority information from the protected resource using accessToken
                        // 2) Map the authority information to one or more GrantedAuthority's and add it to mappedAuthorities

                        // 3) Create a copy of oidcUser but use the mappedAuthorities instead
                        LOGGER.info("============");
                        LOGGER.info(accessToken.getTokenValue());
                        LOGGER.info(oidcUser1.getIdToken().getTokenValue());
                        LOGGER.info(oidcUser1.getAuthorities().toString());
                        LOGGER.info(oidcUser1.getUserInfo().getEmail());
                        LOGGER.info(oidcUser1.getClaims().toString());

                        return (OidcUser) new DefaultOidcUser(mappedAuthorities, oidcUser1.getIdToken(), oidcUser1.getUserInfo());
                    });
        };
    }
}