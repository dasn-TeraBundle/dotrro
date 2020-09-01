package com.innova.doctrro.usrs.service;

import com.innova.doctrro.common.service.ReactiveGenericService;
import reactor.core.publisher.Mono;

import static com.innova.doctrro.common.dto.UserDto.UserDtoRequest;
import static com.innova.doctrro.common.dto.UserDto.UserDtoResponse;

public interface ReactiveUserService extends ReactiveGenericService<UserDtoRequest, UserDtoResponse, String> {
    Mono<UserDtoResponse> findOrCreate(String email, UserDtoRequest request);
    Mono<UserDtoResponse> addRole(String email, String role);

}
