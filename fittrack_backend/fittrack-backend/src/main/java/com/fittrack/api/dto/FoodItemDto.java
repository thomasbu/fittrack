package com.fittrack.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodItemDto {

    private Long id;

    @NotNull(message = "L'ID utilisateur est obligatoire")
    private Long userId;

    @NotBlank(message = "Le nom de l'aliment est obligatoire")
    private String name;

    @NotNull(message = "Les calories sont obligatoires")
    private Integer caloriesPer100gr;

    private Double carbsPer100gr;
    private Double fatPer100gr;
    private Double proteinPer100gr;
}