package com.letsplay.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "L'email mandatory")
    @Email(message = "Had l'email machi valide")
    private String email;

    @NotBlank(message = "L'password mandatory")
    private String password;
}
