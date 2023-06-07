package com.blogging.demo.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User {
    @Id
    private String email;
    private String userName;
    private String password;

    public User() {}

    public User(String email, String userName, String password) {
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }
}
