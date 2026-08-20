package com.fittrack.api.repository;

import com.fittrack.api.model.MealFoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MealFoodItemRepository extends JpaRepository<MealFoodItem, Long> {
    List<MealFoodItem> findByMealId(Long mealId);
}