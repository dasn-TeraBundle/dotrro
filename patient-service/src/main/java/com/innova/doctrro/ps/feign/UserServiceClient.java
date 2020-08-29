package com.innova.doctrro.ps.feign;


import com.innova.doctrro.common.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@FeignClient("user-service/user-service")
public interface UserServiceClient {

    @GetMapping(value = "/roles/{email}")
    Set<String> getRoles(@PathVariable String email);
}