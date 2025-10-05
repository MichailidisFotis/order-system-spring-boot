package com.example.order_system_spring_boot.components.Customers;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;


public class Customer {

    private  String id;

    @NotEmpty(message = "Firstname should not be empty")
    private String firstname;

    @NotEmpty(message = "Surname should not be empty")
    private String surname;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Wrong Email format")
    private String email;


    @NotEmpty(message ="Address should not be empty")
    private String address;


    public Customer(String id, String firstname, String surname, String email , String address) {
        this.id = id;
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
        this.address =  address;
    }

    public Customer() {
        super();
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", firstname='" + firstname + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
