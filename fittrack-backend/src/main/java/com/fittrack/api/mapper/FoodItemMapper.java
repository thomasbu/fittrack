package com.fittrack.api.mapper;

import com.fittrack.api.dto.FoodItemDto;
import com.fittrack.api.model.FoodItem;
import com.fittrack.api.model.User;
import org.springframework.stereotype.Component;

@Component
public class FoodItemMapper {

    public FoodItemDto toDto(FoodItem entity) {
        if (entity == null) {
            return null;
        }

        return FoodItemDto.builder()
                .id(entity.getId())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .name(entity.getName())
                .caloriesPer100gr(entity.getCaloriesPer100gr())
                .carbsPer100gr(entity.getCarbsPer100gr())
                .fatPer100gr(entity.getFatPer100gr())
                .proteinPer100gr(entity.getProteinPer100gr())
                .build();
    }

    public FoodItem toEntity(FoodItemDto dto, User user) {
        if (dto == null) {
            return null;
        }

        return FoodItem.builder()
                .id(dto.getId())
                .user(user)
                .name(dto.getName())
                .caloriesPer100gr(dto.getCaloriesPer100gr())
                .carbsPer100gr(dto.getCarbsPer100gr())
                .fatPer100gr(dto.getFatPer100gr())
                .proteinPer100gr(dto.getProteinPer100gr())
                .build();
    }
}