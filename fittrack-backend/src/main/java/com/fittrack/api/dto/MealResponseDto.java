package com.fittrack.api.dto;

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
public class MealResponseDto {

    private Long id;
    private LocalDateTime date;
    private String mealType;
    private boolean isCheatMeal;
    private String notes;
    private Long userId;

    private List<MealItemResponseDto> items;

    // Totaux nutritionnels calculés pour l'ensemble du repas
    private Double totalCalories;
    private Double totalProteins;
    private Double totalCarbs;
    private Double totalFat;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MealItemResponseDto {
        private Long id;
        private Long foodItemId;
        private String foodName;
        private Double quantityInGrams;

        // Valeurs nutritionnelles calculées selon la quantité consommée
        private Double calories;
        private Double proteins;
        private Double carbs;
        private Double fat;
    }
}