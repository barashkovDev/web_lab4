package com.barashkov.web_lab4.models;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    public User() {}

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    public String name;

    public String email;

    public String password;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
