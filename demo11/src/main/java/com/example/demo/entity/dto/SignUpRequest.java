package com.example.demo.entity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    @NotNull(message =  "username should not be null!")
    @NotBlank(message = "username should not be blank!")
    @Length(min = 5 , max = 35)
    private String username;
    @NotNull(message = "password should not be null!")
    @NotBlank(message = "password should not be blank!")
    @Length(min = 5 , max = 35)
    private String password;
    @Email(message = "invalid email")
    private String email;
    @NotNull(message = "first name should not be null!")
    @NotBlank(message = "fisrt name should not be blank!")
    @Length(min = 3 , max = 10)
    private String firstName;
    @Length(min = 3, max = 10)
    @NotBlank(message = "last name should not be blank!")
    @NotNull(message = "last name should not be null!")
    @Length(min = 3 , max = 10)
    private String lastName;
    private Set<String> roles;
}
