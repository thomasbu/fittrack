package com.fittrack.api.service;

import com.fittrack.api.dto.CreateMealDto;
import com.fittrack.api.dto.MealResponseDto;
import com.fittrack.api.model.FoodItem;
import com.fittrack.api.model.Meal;
import com.fittrack.api.model.MealItem;
import com.fittrack.api.model.User;
import com.fittrack.api.repository.FoodItemRepository;
import com.fittrack.api.repository.MealRepository;
import com.fittrack.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MealService {

    private final MealRepository mealRepository;
    private final FoodItemRepository foodItemRepository;
    private final UserRepository userRepository;

    /**
     * Enregistre un nouveau repas avec ses aliment(s) et calcule la réponse complète.
     */
    @Transactional
    public MealResponseDto createMeal(CreateMealDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + dto.getUserId()));

        Meal meal = Meal.builder()
                .date(dto.getDate())
                .mealType(dto.getMealType())
                .isCheatMeal(dto.isCheatMeal())
                .notes(dto.getNotes())
                .user(user)
                .mealItems(new ArrayList<>())
                .build();

        if (dto.getItems() != null && !dto.getItems().isEmpty()) {
            for (CreateMealDto.MealItemDto itemDto : dto.getItems()) {
                FoodItem foodItem = foodItemRepository.findById(itemDto.getFoodItemId())
                        .orElseThrow(() -> new RuntimeException("Aliment non trouvé avec l'ID : " + itemDto.getFoodItemId()));

                MealItem mealItem = MealItem.builder()
                        .meal(meal)
                        .foodItem(foodItem)
                        .quantityInGrams(itemDto.getQuantityInGrams())
                        .build();

                meal.getMealItems().add(mealItem);
            }
        }

        Meal savedMeal = mealRepository.save(meal);
        return mapToResponseDto(savedMeal);
    }

    /**
     * Récupère un repas par son ID avec les totaux nutritionnels calculés.
     */
    @Transactional(readOnly = true)
    public MealResponseDto getMealById(Long id) {
        Meal meal = mealRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Repas non trouvé avec l'ID : " + id));
        return mapToResponseDto(meal);
    }

    /**
     * Récupère tous les repas d'un utilisateur donné.
     */
    @Transactional(readOnly = true)
    public List<MealResponseDto> getMealsByUserId(Long userId) {
        return mealRepository.findByUserId(userId).stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    /**
     * Mappe l'entité Meal vers MealResponseDto en calculant les macros individuelles
     * et les totaux globaux au prorata de la quantité consommée (pour 100g).
     */
    public MealResponseDto mapToResponseDto(Meal meal) {
        double totalCalories = 0.0;
        double totalProteins = 0.0;
        double totalCarbs = 0.0;
        double totalFat = 0.0;

        List<MealResponseDto.MealItemResponseDto> itemDtos = new ArrayList<>();

        if (meal.getMealItems() != null) {
            for (MealItem item : meal.getMealItems()) {
                FoodItem food = item.getFoodItem();
                
                // Ratio basé sur la valeur nutritionnelle pour 100g
                double factor = item.getQuantityInGrams() / 100.0;

                double calories = food.getCaloriesPer100gr() * factor;
                double proteins = food.getProteinPer100gr() * factor;
                double carbs = food.getCarbsPer100gr() * factor;
                double fat = food.getFatPer100gr() * factor;

                totalCalories += calories;
                totalProteins += proteins;
                totalCarbs += carbs;
                totalFat += fat;

                itemDtos.add(MealResponseDto.MealItemResponseDto.builder()
                        .id(item.getId())
                        .foodItemId(food.getId())
                        .foodName(food.getName())
                        .quantityInGrams(item.getQuantityInGrams())
                        .calories(roundTwoDecimals(calories))
                        .proteins(roundTwoDecimals(proteins))
                        .carbs(roundTwoDecimals(carbs))
                        .fat(roundTwoDecimals(fat))
                        .build());
            }
        }

        return MealResponseDto.builder()
                .id(meal.getId())
                .date(meal.getDate())
                .mealType(meal.getMealType())
                .isCheatMeal(meal.isCheatMeal())
                .notes(meal.getNotes())
                .userId(meal.getUser() != null ? meal.getUser().getId() : null)
                .items(itemDtos)
                .totalCalories(roundTwoDecimals(totalCalories))
                .totalProteins(roundTwoDecimals(totalProteins))
                .totalCarbs(roundTwoDecimals(totalCarbs))
                .totalFat(roundTwoDecimals(totalFat))
                .build();
    }

    private double roundTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}