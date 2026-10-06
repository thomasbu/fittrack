package com.fittrack.api.service;

import com.fittrack.api.dto.MealItemDto;
import com.fittrack.api.mapper.MealItemMapper;
import com.fittrack.api.model.FoodItem;
import com.fittrack.api.model.Meal;
import com.fittrack.api.model.MealItem;
import com.fittrack.api.repository.FoodItemRepository;
import com.fittrack.api.repository.MealItemRepository;
import com.fittrack.api.repository.MealRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MealItemService {

    private final MealItemRepository mealItemRepository;
    private final MealRepository mealRepository;
    private final FoodItemRepository foodItemRepository;
    private final MealItemMapper mealItemMapper;

    @Transactional(readOnly = true)
    public List<MealItemDto> getItemsByMealId(Long mealId) {
        if (!mealRepository.existsById(mealId)) {
            throw new EntityNotFoundException("Repas non trouvé avec l'ID : " + mealId);
        }

        return mealItemRepository.findByMealId(mealId)
                .stream()
                .map(mealItemMapper::toDto)
                .toList();
    }

    @Transactional
    public MealItemDto addFoodToMeal(MealItemDto dto) {
        Meal meal = mealRepository.findById(dto.getMealId())
                .orElseThrow(() -> new EntityNotFoundException("Repas non trouvé avec l'ID : " + dto.getMealId()));

        FoodItem foodItem = foodItemRepository.findById(dto.getFoodItemId())
                .orElseThrow(() -> new EntityNotFoundException("Aliment non trouvé avec l'ID : " + dto.getFoodItemId()));

        MealItem mealItem = mealItemMapper.toEntity(dto, meal, foodItem);
        MealItem savedItem = mealItemRepository.save(mealItem);

        return mealItemMapper.toDto(savedItem);
    }

    @Transactional
    public MealItemDto updateMealItemQuantity(Long itemId, Double newQuantity) {
        MealItem mealItem = mealItemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item de repas non trouvé avec l'ID : " + itemId));

        mealItem.setQuantityInGrams(newQuantity);
        MealItem updatedItem = mealItemRepository.save(mealItem);

        return mealItemMapper.toDto(updatedItem);
    }

    @Transactional
    public void removeFoodFromMeal(Long itemId) {
        if (!mealItemRepository.existsById(itemId)) {
            throw new EntityNotFoundException("Item de repas non trouvé avec l'ID : " + itemId);
        }
        mealItemRepository.deleteById(itemId);
    }
}