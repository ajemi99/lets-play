package com.letsplay.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Smittek ma khasshach tkon khawya")
    private String name;

    @NotBlank(message = "L'email mandatory (darouri)")
    @Email(message = "Had l'email machi valide")
    private String email;

    @NotBlank(message = "L'password mandatory (darouri)")
    @Size(min = 6, message = "L'password khasso 3la l'a9al 6 dyal les caractères")
    private String password;

    @NotBlank(message = "L'role mandatory (ADMIN aw USER)")
    private String role; 
}
