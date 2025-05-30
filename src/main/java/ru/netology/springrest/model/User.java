package ru.netology.springrest.model;

import java.util.List;

import jakarta.validation.constraints.*;

public class User {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_.-]*$",
            message = "Password must contain letters or numbers")
    private String password;

    private List<Authorities> authorities = null;

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        authorities = null;
    }

    public User(String name, String password, List<Authorities> authorities) {
        this.name = name;
        this.password = password;
        this.authorities = authorities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Authorities> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authorities> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString(){
        return name;
    }
}
