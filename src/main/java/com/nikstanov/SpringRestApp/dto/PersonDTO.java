package com.nikstanov.SpringRestApp.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class PersonDTO {

    @NotEmpty(message = "Name is empty!")
    @Size(min = 2, max = 30, message = "2 < size < 30")
    private String name;

    @Min(value = 0, message = "age > 0")
    private int age;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Email(message = "incorrect email")
    @NotEmpty(message = "empty email")
    private String email;
}
