package com.example.order_system_spring_boot.components.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class Login {

    @NotEmpty(message =  "Email is required to login")
    @Email(message =  "Wrong email format")
    private String  email ;

    @NotEmpty(message = "Password is required to login")
    private String password;

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "Login{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
