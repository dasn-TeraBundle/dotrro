package com.innova.doctrro.usrs.controller;


import com.innova.doctrro.usrs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.innova.doctrro.common.dto.UserDto.UserDtoRequest;
import static com.innova.doctrro.common.dto.UserDto.UserDtoResponse;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON_VALUE;


@RestController
@RequestMapping("/user-service")
public class UserController {

    private final UserService userService;
    private final Set<String> roles = new HashSet<>();

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        roles.add("ROLE_USER");
    }

    @GetMapping(value = "/me")
    public Mono<UserDtoResponse> getOrCreate(Mono<BearerTokenAuthentication> auth) {
        return auth.map(a -> {
            System.out.println(a.getToken().getTokenValue());
            Map<String, Object> details = a.getTokenAttributes();
            var req = new UserDtoRequest(details.get("email").toString(), details.get("name").toString(), roles);

            return userService.findOrCreate(req.getEmail(), req);
        });
    }

    @GetMapping(value = "/roles/{email}", produces = APPLICATION_JSON_VALUE)
    public Set<String> getOrCreate(@PathVariable String email) {
        return userService.findById(email).getRoles();
    }
}
