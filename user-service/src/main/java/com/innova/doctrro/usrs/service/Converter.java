package com.innova.doctrro.usrs.service;

import com.innova.doctrro.usrs.dto.UserDtoRequest;
import com.innova.doctrro.common.dto.UserDto.UserDtoResponse;
import com.innova.doctrro.usrs.beans.User;

import java.util.List;
import java.util.stream.Collectors;

public interface Converter {

    static User convert(UserDtoRequest request) {
        var user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setRoles(request.getRoles());
        user.setActive(request.isActive());
        user.setEnabled(request.isEnabled());

        return user;
    }

    static UserDtoResponse convert(User user) {
        return new UserDtoResponse(user.getEmail(), user.getName(), user.getRoles(), user.isEnabled(), user.isActive());
    }

    static List<UserDtoResponse> convert(List<User> users) {
        return users
                .stream()
                .map(Converter::convert)
                .collect(Collectors.toList());
    }
}
