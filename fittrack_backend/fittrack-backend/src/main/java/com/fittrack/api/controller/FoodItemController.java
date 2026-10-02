package com.fittrack.api.controller;

import com.fittrack.api.dto.FoodItemDto;
import com.fittrack.api.service.FoodItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food-items")
@RequiredArgsConstructor
public class FoodItemController {

    private final FoodItemService foodItemService;

    @GetMapping("/{id}")
    public ResponseEntity<FoodItemDto> getFoodItemById(@PathVariable Long id) {
        FoodItemDto foodItem = foodItemService.getFoodItemById(id);
        return ResponseEntity.ok(foodItem);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FoodItemDto>> getFoodItemsByUserId(@PathVariable Long userId) {
        List<FoodItemDto> foodItems = foodItemService.getFoodItemsByUserId(userId);
        return ResponseEntity.ok(foodItems);
    }

    @PostMapping
    public ResponseEntity<FoodItemDto> createFoodItem(@Valid @RequestBody FoodItemDto dto) {
        FoodItemDto createdFoodItem = foodItemService.createFoodItem(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFoodItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoodItem(@PathVariable Long id) {
        foodItemService.deleteFoodItem(id);
        return ResponseEntity.noContent().build();
    }
}