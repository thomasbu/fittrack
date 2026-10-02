package com.fittrack.api.controller;

import com.fittrack.api.dto.MealItemDto;
import com.fittrack.api.service.MealItemService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meal-items")
@RequiredArgsConstructor
@Validated
public class MealItemController {

    private final MealItemService mealItemService;

    @GetMapping("/meal/{mealId}")
    public ResponseEntity<List<MealItemDto>> getItemsByMealId(@PathVariable Long mealId) {
        List<MealItemDto> items = mealItemService.getItemsByMealId(mealId);
        return ResponseEntity.ok(items);
    }

    @PostMapping
    public ResponseEntity<MealItemDto> addFoodToMeal(@Valid @RequestBody MealItemDto dto) {
        MealItemDto createdItem = mealItemService.addFoodToMeal(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<MealItemDto> updateQuantity(
            @PathVariable Long itemId,
            @RequestParam @Positive(message = "La quantité doit être supérieure à 0") Double quantity) {
        MealItemDto updatedItem = mealItemService.updateMealItemQuantity(itemId, quantity);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> removeFoodFromMeal(@PathVariable Long itemId) {
        mealItemService.removeFoodFromMeal(itemId);
        return ResponseEntity.noContent().build();
    }
}