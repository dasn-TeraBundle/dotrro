package com.innova.doctrro.adms.config;

import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableResourceServer
public class SecurityConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http.authorizeRequests().antMatchers("/**").permitAll();
    }

//    @Bean
//    public PrincipalExtractor principalExtractor() {
//        return map -> {
//            System.out.println("Principal extracted.");
//            System.out.println(map);
//            return map;
//        };
//    }

    @Bean
    public AuthoritiesExtractor authoritiesExtractor() {
        return map -> {
            System.out.println("Authorities ");
            System.out.println(map);
            List<GrantedAuthority> lst = new ArrayList<>();
            lst.add(new SimpleGrantedAuthority("ROLE_PATIENT"));
            return lst;
        };
    }
}
