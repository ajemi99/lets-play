package com.letsplay.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank(message = "Smit l'produit ma khasshach tkon khawya")
    private String name;

    @NotBlank(message = "L'description mandatory")
    private String description;

    @NotNull(message = "L'prix mandatory")
    @Positive(message = "L'prix khasso ykon kber mn 0")
    private Double price;
}
