package com.innova.doctrro.common.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

public class UserDto {

    private UserDto() {
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UserDtoRequest {
        @NotNull
        private String email;
        @NotNull
        private String name;
        @NotNull
        private Set<String> roles;
        @NotNull
        private boolean enabled;
        @NotNull
        private boolean active;

        public UserDtoRequest(@NotNull String email, @NotNull String name, @NotNull Set<String> roles) {
            this(email, name, roles, false);
        }

        public UserDtoRequest(@NotNull String email, @NotNull String name, @NotNull Set<String> roles, @NotNull boolean enabled) {
            this(email, name, roles, enabled, false);
        }

        public UserDtoRequest(@NotNull String email, @NotNull String name, @NotNull Set<String> roles, @NotNull boolean enabled, @NotNull boolean active) {
            this.email = email;
            this.name = name;
            this.roles = roles;
            this.enabled = enabled;
            this.active = active;
        }
    }


    @NoArgsConstructor
    public static class UserDtoResponse extends UserDtoRequest implements Serializable {
        public UserDtoResponse(@NotNull String email, @NotNull String name, @NotNull Set<String> roles, @NotNull boolean isEnabled, @NotNull boolean isActive) {
            super(email, name, roles, isEnabled, isActive);
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
