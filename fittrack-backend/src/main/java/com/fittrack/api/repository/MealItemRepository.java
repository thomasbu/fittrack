package com.fittrack.api.repository;

import com.fittrack.api.model.MealItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealItemRepository extends JpaRepository<MealItem, Long> {

    // Récupère tous les aliments/portions associés à un repas donné
    List<MealItem> findByMealId(Long mealId);

    // Permet de supprimer tous les items d'un repas en une seule opération
    void deleteByMealId(Long mealId);
}