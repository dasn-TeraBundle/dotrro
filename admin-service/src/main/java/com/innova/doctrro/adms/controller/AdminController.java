package com.innova.doctrro.adms.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
public class AdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @PreAuthorize("hasAnyAuthority('ROLE_PATIENT')")
    @GetMapping
    public Object hello(OAuth2Authentication auth) {
        Map<String, Object> details = (Map<String, Object>) auth.getUserAuthentication().getDetails();
        details.forEach((k, v) -> LOGGER.info(k + " " + v));
        System.out.println(auth.getAuthorities());
        return details;
    }
}
