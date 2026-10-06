package com.fittrack.api.controller;

import com.fittrack.api.dto.CreateMealDto;
import com.fittrack.api.dto.MealResponseDto;
import com.fittrack.api.service.MealService;
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

    /**
     * POST /api/meals
     * Crée un nouveau repas et retourne le repas enrichi des calculs nutritionnels.
     */
    @PostMapping
    public ResponseEntity<MealResponseDto> createMeal(@RequestBody CreateMealDto dto) {
        MealResponseDto response = mealService.createMeal(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/meals/{id}
     * Récupère un repas spécifique par son identifiant.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MealResponseDto> getMealById(@PathVariable Long id) {
        MealResponseDto response = mealService.getMealById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/meals/user/{userId}
     * Récupère l'historique complet des repas d'un utilisateur.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MealResponseDto>> getMealsByUserId(@PathVariable Long userId) {
        List<MealResponseDto> response = mealService.getMealsByUserId(userId);
        return ResponseEntity.ok(response);
    }
}