package com.innova.doctrro.usrs.beans;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "users")
public class User implements Serializable {

    @Id
    private String email;
    private String name;
    private Set<String> roles;
    private boolean isEnabled;
    private boolean isActive;

    public void addRole(String role) {
        if (roles == null) {
            roles = new HashSet<>();
        }

        roles.add(role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}
