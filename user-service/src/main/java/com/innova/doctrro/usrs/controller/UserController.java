package com.innova.doctrro.usrs.controller;


import com.innova.doctrro.usrs.service.ReactiveUserService;
import com.innova.doctrro.usrs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.innova.doctrro.usrs.dto.UserDtoRequest;
import static com.innova.doctrro.common.dto.UserDto.UserDtoResponse;


@RestController
@RequestMapping("/user-service")
public class UserController {

    private final UserService userService;
    private final ReactiveUserService reactiveUserService;
    private final Set<String> roles = new HashSet<>();

    @Autowired
    public UserController(UserService userService, ReactiveUserService reactiveUserService) {
        this.userService = userService;
        this.reactiveUserService = reactiveUserService;
        roles.add("ROLE_USER");
    }

    @GetMapping(value = "/me")
    public UserDtoResponse getOrCreate(BearerTokenAuthentication auth) {
        System.out.println(auth.getToken().getTokenValue());
        Map<String, Object> details = auth.getTokenAttributes();
        var req = new UserDtoRequest(details.get("email").toString(), details.get("name").toString(), roles);

        return userService.findOrCreate(req.getEmail(), req);
    }

//    @GetMapping(value = "/roles/{email}", produces = APPLICATION_JSON_VALUE)
//    public Set<String> getRoles(@PathVariable String email) {
//        return userService.findById(email).getRoles();
//    }

    @GetMapping(value = "/v2/me")
    public Mono<UserDtoResponse> getOrCreate(Mono<BearerTokenAuthentication> auth) {
        return auth.map(a -> {
            System.out.println(a.getToken().getTokenValue());
            return a.getTokenAttributes();
        }).flatMap(details -> {
            var req = new UserDtoRequest(details.get("email").toString(), details.get("name").toString(), roles);

            return reactiveUserService.findOrCreate(req.getEmail(), req);
        }).log();
    }

//    @GetMapping(value = "/v2/roles/{email}", produces = APPLICATION_JSON_VALUE)
//    public Set<String> getRolesV2(@PathVariable String email) {
//        return reactiveUserService.findById(email).block().getRoles();
//    }

}
