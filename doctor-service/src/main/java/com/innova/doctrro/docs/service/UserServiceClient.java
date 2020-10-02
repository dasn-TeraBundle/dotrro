package com.innova.doctrro.docs.service;


import com.innova.doctrro.common.dto.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient("user-service")
public interface UserServiceClient {

    @GetMapping(value = "/user-service/me")
    Mono<UserDto.UserDtoResponse> getOrCreate(@RequestHeader(value = "Authorization") String bearerToken);
}