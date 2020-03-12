package com.jotov.myaskme.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class CreateUserDto {
    @NotBlank(message = "Username cannot be empty")
    private String username;
    @Email(message = "Email is not correct")
    @NotBlank(message = "Email cannot be empty")
    private String email;
    @NotBlank(message = "Password cannot be empty")
    private String password;
    @NotBlank(message = "Password confirmation cannot be empty")
    private String passwordConfirmation;
}
