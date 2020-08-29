package com.innova.doctrro.ps.config;

import com.innova.doctrro.ps.feign.UserServiceClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableResourceServer
public class SecurityConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private UserServiceClient userServiceClient;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**")
                .authenticated();
    }

    @Bean
    public AuthoritiesExtractor authoritiesExtractor() {
        return map -> {
            List<GrantedAuthority> lst = new ArrayList<>();
            try {
                userServiceClient.getRoles(map.get("email").toString())
                        .forEach(role -> lst.add(new SimpleGrantedAuthority(role)));
            } catch (FeignException ex) {
                ex.printStackTrace();
                lst.add(new SimpleGrantedAuthority("ROLE_USER"));
            }

            return lst;
        };
    }
}
