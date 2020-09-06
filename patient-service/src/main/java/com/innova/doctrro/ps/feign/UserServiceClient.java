package com.innova.doctrro.ps.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import java.util.Set;

@ReactiveFeignClient("user-service")
//@FeignClient("user-service/user-service")
public interface UserServiceClient {

    @GetMapping(value = "/user-service/roles/{email}")
    Mono<Set<String>> getRoles(@PathVariable String email);
}