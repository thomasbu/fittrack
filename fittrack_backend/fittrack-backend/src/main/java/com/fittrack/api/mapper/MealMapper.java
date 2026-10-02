package com.fittrack.api.mapper;

import com.fittrack.api.dto.MealDto;
import com.fittrack.api.model.Meal;
import com.fittrack.api.model.User;
import org.springframework.stereotype.Component;

@Component
public class MealMapper {

    public MealDto toDto(Meal entity) {
        if (entity == null) {
            return null;
        }

        return MealDto.builder()
                .id(entity.getId())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .date(entity.getDate())
                .mealType(entity.getMealType())
                .isCheatMeal(entity.isCheatMeal()) // Ajouté ici
                .notes(entity.getNotes())
                .build();
    }

    public Meal toEntity(MealDto dto, User user) { // Retrait de ActivityType
        if (dto == null) {
            return null;
        }

        return Meal.builder()
                .id(dto.getId())
                .user(user)
                .date(dto.getDate())
                .mealType(dto.getMealType())
                .notes(dto.getNotes())
                .isCheatMeal(dto.isCheatMeal())
                .build();
    }
}