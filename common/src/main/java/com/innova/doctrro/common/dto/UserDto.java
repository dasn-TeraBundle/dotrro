package com.innova.doctrro.common.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

public class UserDto {

    private UserDto() {
    }


    @Getter
    @Setter
    @NoArgsConstructor
    public static class UserDtoResponse implements Serializable {

        private String email;
        private String name;
        private Set<String> roles;
        private boolean enabled;
        private boolean active;

        public UserDtoResponse(String email, String name, Set<String> roles, boolean enabled, boolean active) {
            this.email = email;
            this.name = name;
            this.roles = roles;
            this.enabled = enabled;
            this.active = active;
        }

        @Override
        public String toString() {
            return "UserDtoResponse{" +
                    "email='" + getEmail() + '\'' +
                    ", name='" + getName() + '\'' +
                    ", roles=" + getRoles() +
                    ", isEnabled=" + isEnabled() +
                    ", isActive=" + isActive() +
                    '}';
        }
    }
}
