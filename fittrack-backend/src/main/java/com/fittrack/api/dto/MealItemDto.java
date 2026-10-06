package com.fittrack.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealItemDto {

    private Long id;

    private Long mealId;

    @NotNull(message = "L'ID de l'aliment est obligatoire")
    private Long foodItemId;

    private String foodItemName;

    @NotNull(message = "La quantité est obligatoire")
    @Positive(message = "La quantité doit être supérieure à 0")
    private Double quantityInGrams;

    private Integer calculatedCalories;
    private Double calculatedProtein;
    private Double calculatedCarbs;
    private Double calculatedFat;
}