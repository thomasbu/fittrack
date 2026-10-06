package com.fittrack.api.mapper;

import com.fittrack.api.dto.MealItemDto;
import com.fittrack.api.model.FoodItem;
import com.fittrack.api.model.Meal;
import com.fittrack.api.model.MealItem;
import org.springframework.stereotype.Component;

@Component
public class MealItemMapper {

    public MealItemDto toDto(MealItem entity) {
        if (entity == null) {
            return null;
        }

        FoodItem food = entity.getFoodItem();
        Double qty = entity.getQuantityInGrams();

        // Calculs au prorata des 100g
        Integer calculatedCalories = null;
        Double calculatedProtein = null;
        Double calculatedCarbs = null;
        Double calculatedFat = null;

        if (food != null && qty != null) {
            double ratio = qty / 100.0;
            if (food.getCaloriesPer100gr() != null) {
                calculatedCalories = (int) Math.round(food.getCaloriesPer100gr() * ratio);
            }
            if (food.getProteinPer100gr() != null) {
                calculatedProtein = food.getProteinPer100gr() * ratio;
            }
            if (food.getCarbsPer100gr() != null) {
                calculatedCarbs = food.getCarbsPer100gr() * ratio;
            }
            if (food.getFatPer100gr() != null) {
                calculatedFat = food.getFatPer100gr() * ratio;
            }
        }

        return MealItemDto.builder()
                .id(entity.getId())
                .mealId(entity.getMeal() != null ? entity.getMeal().getId() : null)
                .foodItemId(food != null ? food.getId() : null)
                .foodItemName(food != null ? food.getName() : null)
                .quantityInGrams(qty)
                .calculatedCalories(calculatedCalories)
                .calculatedProtein(calculatedProtein)
                .calculatedCarbs(calculatedCarbs)
                .calculatedFat(calculatedFat)
                .build();
    }

    public MealItem toEntity(MealItemDto dto, Meal meal, FoodItem foodItem) {
        if (dto == null) {
            return null;
        }

        return MealItem.builder()
                .id(dto.getId())
                .meal(meal)
                .foodItem(foodItem)
                .quantityInGrams(dto.getQuantityInGrams())
                .build();
    }
}