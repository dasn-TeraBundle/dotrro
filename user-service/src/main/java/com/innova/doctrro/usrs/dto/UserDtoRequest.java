package com.innova.doctrro.usrs.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDtoRequest {
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
