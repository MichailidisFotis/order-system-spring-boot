package com.example.order_system_spring_boot.components.users;

import com.example.order_system_spring_boot.helpers.ValidationGroups;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.stereotype.Component;


public class User implements ValidationGroups {
    private String id;

    @NotEmpty(message="Firstname is not allowed to be empty" , groups ={OnCreate.class})
    private String firstname;

    @NotEmpty(message="Surname is not allowed to be empty", groups ={OnCreate.class})
    private String surname;

    @NotEmpty(message="Email is not allowed to be empty", groups ={OnCreate.class})
    @Email(message ="Incorrect format" , groups ={OnCreate.class , OnUpdate.class})
    private String email;

    @NotEmpty(message="Password is not allowed to be empty", groups ={OnCreate.class})
    private String password;


    public User() {

    }

    public User(String id, String firstname, String surname, String email, String password) {
        this.id = id;
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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
        return "User{" +
                "id='" + id + '\'' +
                ", firstname='" + firstname + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
