package com.innova.doctrro.ps.config;

import com.innova.doctrro.ps.feign.UserServiceClient;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.TokenIntrospectionErrorResponse;
import com.nimbusds.oauth2.sdk.TokenIntrospectionResponse;
import com.nimbusds.oauth2.sdk.TokenIntrospectionSuccessResponse;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.id.Audience;
import feign.FeignException;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.BadOpaqueTokenException;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionException;
import org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URL;
import java.time.Instant;
import java.util.*;


public class UserInfoOpaqueTokenIntrospector implements ReactiveOpaqueTokenIntrospector {

    private final WebClient webClient = WebClient.create();
    private final String authorityPrefix = "ROLE_";
    private final URI introspectionUri = URI.create("https://www.googleapis.com/oauth2/v3/userinfo");
    private UserServiceClient userServiceClient;

    public UserInfoOpaqueTokenIntrospector(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @Override
    public Mono<OAuth2AuthenticatedPrincipal> introspect(String token) {
        return Mono.just(token)
                .flatMap(this::makeRequest)
                .flatMap(this::adaptToNimbusResponse)
                .flatMap(this::parseNimbusResponse)
                .map(this::castToNimbusSuccess)
                .doOnNext((response) -> {
                    this.validate(token, response);
                }).map(this::convertClaimsSet)
                .onErrorMap((e) -> {
                    return !(e instanceof OAuth2IntrospectionException);
                }, this::onError)
                .log();
    }

    private Mono<ClientResponse> makeRequest(String token) {
        return ((WebClient.RequestBodySpec)
                (this.webClient.get().uri(this.introspectionUri))
                        .header("Accept", new String[]{"application/json;charset=UTF-8"}))
                .header("Authorization", "Bearer " + token)
                .exchange();
    }

    private Mono<HTTPResponse> adaptToNimbusResponse(ClientResponse responseEntity) {
        HTTPResponse response = new HTTPResponse(responseEntity.rawStatusCode());
        response.setHeader("Content-Type", responseEntity.headers().contentType().get().toString());
        if (response.getStatusCode() != 200) {
            return responseEntity.bodyToFlux(DataBuffer.class).map(DataBufferUtils::release).then(Mono.error(new OAuth2IntrospectionException("Introspection endpoint responded with " + response.getStatusCode())));
        } else {
            Mono<String> var10000 = responseEntity.bodyToMono(String.class);
//            response.getClass();
            return var10000.doOnNext(response::setContent).map((body) -> {
                return response;
            });
        }
    }

    private Mono<TokenIntrospectionResponse> parseNimbusResponse(HTTPResponse response) {
        try {
            if (response.getStatusCode() == 200) {
                response.ensureStatusCode(200);
                Date d = new Date();
                JSONObject jsonObject = response.getContentAsJSONObject();
                jsonObject.put("active", true);

                return userServiceClient.getRoles(jsonObject.get("email").toString())
                        .map(roles -> String.join(",", roles))
                        .map(roles -> {
                            jsonObject.put("scope", roles);
                            return jsonObject;
                        })
                        .map(jsonObject1 -> {
                            try {
                                return TokenIntrospectionSuccessResponse.parse(jsonObject1);
                            } catch (Exception e) {
                               throw new OAuth2IntrospectionException(e.getMessage(), e);
                            }
                        });
//                try {
//                    Set<String> roles = userServiceClient.getRoles(jsonObject.get("email").toString()).block();
//                    jsonObject.put("scope", String.join(",", roles));
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                    jsonObject.put("scope", "ROLE_USER");
//                }
//                return Mono.just(TokenIntrospectionSuccessResponse.parse(jsonObject));
            } else {
                return Mono.just(TokenIntrospectionErrorResponse.parse(response));
            }
        } catch (Exception var3) {
            return Mono.error(new OAuth2IntrospectionException(var3.getMessage(), var3));
        }
    }

    private TokenIntrospectionSuccessResponse castToNimbusSuccess(TokenIntrospectionResponse introspectionResponse) {
        if (!introspectionResponse.indicatesSuccess()) {
            throw new OAuth2IntrospectionException("Token introspection failed");
        } else {
            return (TokenIntrospectionSuccessResponse) introspectionResponse;
        }
    }

    private void validate(String token, TokenIntrospectionSuccessResponse response) {
        if (!response.isActive()) {
            throw new BadOpaqueTokenException("Provided token isn't active");
        }
    }

    private OAuth2AuthenticatedPrincipal convertClaimsSet(TokenIntrospectionSuccessResponse response) {
        Map<String, Object> claims = response.toJSONObject();
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        Iterator var5;
        if (response.getAudience() != null) {
            List<String> audiences = new ArrayList<>();
            var5 = response.getAudience().iterator();

            while (var5.hasNext()) {
                Audience audience = (Audience) var5.next();
                audiences.add(audience.getValue());
            }

            claims.put("aud", Collections.unmodifiableList(audiences));
        }

        if (response.getClientID() != null) {
            claims.put("client_id", response.getClientID().getValue());
        }

        Instant iat;
        if (response.getExpirationTime() != null) {
            iat = response.getExpirationTime().toInstant();
            claims.put("exp", iat);
        }

        if (response.getIssueTime() != null) {
            iat = response.getIssueTime().toInstant();
            System.out.println(iat);
            claims.put("iat", iat);
        }

        if (response.getIssuer() != null) {
            claims.put("iss", this.issuer(response.getIssuer().getValue()));
        }

        if (response.getNotBeforeTime() != null) {
            claims.put("nbf", response.getNotBeforeTime().toInstant());
        }

        if (response.getScope() != null) {
            List<String> scopes = Collections.unmodifiableList(response.getScope().toStringList());
            claims.put("scope", scopes);
            var5 = scopes.iterator();

            while (var5.hasNext()) {
                String scope = (String) var5.next();
                authorities.add(new SimpleGrantedAuthority(scope));
            }
        }

        System.out.println(authorities);
        return new DefaultOAuth2AuthenticatedPrincipal(claims, authorities);
    }

    private URL issuer(String uri) {
        try {
            return new URL(uri);
        } catch (Exception var3) {
            throw new OAuth2IntrospectionException("Invalid iss value: " + uri);
        }
    }

    private OAuth2IntrospectionException onError(Throwable e) {
        return new OAuth2IntrospectionException(e.getMessage(), e);
    }

}
