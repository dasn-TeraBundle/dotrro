package com.innova.doctrro.docs.config;

import com.innova.doctrro.common.security.CustomNimbusReactiveOpaqueTokenIntrospector;
import com.innova.doctrro.docs.service.UserServiceClient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionException;
import org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


public class UserInfoOpaqueTokenIntrospector implements ReactiveOpaqueTokenIntrospector {

    private final CustomNimbusReactiveOpaqueTokenIntrospector delegate = new CustomNimbusReactiveOpaqueTokenIntrospector();
    private final UserServiceClient userServiceClient;

    public UserInfoOpaqueTokenIntrospector(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @Override
    public Mono<OAuth2AuthenticatedPrincipal> introspect(String token) {
        return this.delegate.introspect(token)
                .flatMap(principal -> {
                    Map<String, Object> attributes = principal.getAttributes();
                    return userServiceClient.getRoles(principal.getAttributes().get("email").toString())
                            .map(roles -> {
                                Collection<GrantedAuthority> authorities = new ArrayList<>();
                                roles.stream().map(SimpleGrantedAuthority::new).forEach(authorities::add);
                                return authorities;
                            })
                            .map(roles -> new DefaultOAuth2AuthenticatedPrincipal(attributes, roles))
                            .map(p -> (OAuth2AuthenticatedPrincipal) p);
                })
                .onErrorMap((e) -> {
                    return !(e instanceof OAuth2IntrospectionException);
                }, this::onError)
                .log();
    }

    private OAuth2IntrospectionException onError(Throwable e) {
        return new OAuth2IntrospectionException(e.getMessage(), e);
    }


}
