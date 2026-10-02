package com.fittrack.api.controller;

import com.fittrack.api.dto.MealDto;
import com.fittrack.api.service.MealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meals")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;

    @GetMapping("/{id}")
    public ResponseEntity<MealDto> getMealById(@PathVariable Long id) {
        MealDto meal = mealService.getMealById(id);
        return ResponseEntity.ok(meal);
    }

    @GetMapping("/user/{userId}") // Correction ici : userId
    public ResponseEntity<List<MealDto>> getMealsByUserId(@PathVariable Long userId) {
        List<MealDto> meals = mealService.getMealsByUserId(userId);
        return ResponseEntity.ok(meals);
    }

    @PostMapping
    public ResponseEntity<MealDto> createMeal(@Valid @RequestBody MealDto dto) {
        MealDto createdMeal = mealService.createMeal(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMeal);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long id) {
        mealService.deleteMeal(id);
        return ResponseEntity.noContent().build();
    }
}