package com.fittrack.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMealDto {

    @NotNull(message = "La date est obligatoire")
    private LocalDateTime date;

    private String mealType;
    private boolean isCheatMeal;
    private String notes;

    @NotNull(message = "L'ID utilisateur est obligatoire")
    private Long userId;

    @NotEmpty(message = "Un repas doit contenir au moins un aliment")
    private List<MealItemDto> items;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MealItemDto {
        @NotNull(message = "L'ID de l'aliment est obligatoire")
        private Long foodItemId;

        @NotNull(message = "La quantité est obligatoire")
        private Double quantityInGrams;
    }
}