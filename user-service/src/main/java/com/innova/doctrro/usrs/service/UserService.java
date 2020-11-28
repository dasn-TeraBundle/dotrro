package com.innova.doctrro.usrs.service;

import com.innova.doctrro.common.service.GenericService;

import com.innova.doctrro.usrs.dto.UserDtoRequest;
import static com.innova.doctrro.common.dto.UserDto.UserDtoResponse;

public interface UserService extends GenericService<UserDtoRequest, UserDtoResponse, String> {
    UserDtoResponse findOrCreate(String email, UserDtoRequest request);
    UserDtoResponse addRole(String email, String role);

}
