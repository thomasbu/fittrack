import { Component, inject, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { toSignal } from '@angular/core/rxjs-interop';
import { switchMap, startWith } from 'rxjs';
import { MealService } from '../../services/meal';
import { MealResponseDto } from '../../models/meal.model';

@Component({
  selector: 'app-meal-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './meal-list.html',
  styleUrl: './meal-list.css',
})
export class MealList {
  private mealService = inject(MealService);
  private currentUserId = 1;

  // Flux réactif : se charge au démarrage et se recharge automatiquement
  // dès que mealRefresh$ émet un nouveau signal (après un createMeal).
  meals = toSignal(
    this.mealService.mealRefresh$.pipe(
      startWith(0),
      switchMap(() => this.mealService.getMealsByUserId(this.currentUserId))
    ),
    { initialValue: [] }
  );

  // La méthode appelée par ton bouton HTML
  loadMeals(): void {
    // En incrémentant ou en déclenchant un flux, tu forces la recharge
    // Ou plus simple : tu peux réutiliser le même mécanisme de trigger du service
    this.mealService.triggerRefresh(); // (on va voir ça juste en dessous)
  }

  isLoading = signal<boolean>(false);
  error = signal<string | null>(null);

  // Computeds pour le résumé de la journée
  dailyCalories = computed(() =>
    this.meals().reduce((acc, meal) => acc + (meal.totalCalories || 0), 0)
  );
  dailyProteins = computed(() =>
    this.meals().reduce((acc, meal) => acc + (meal.totalProteins || 0), 0)
  );
  dailyCarbs = computed(() => this.meals().reduce((acc, meal) => acc + (meal.totalCarbs || 0), 0));
  dailyFat = computed(() => this.meals().reduce((acc, meal) => acc + (meal.totalFat || 0), 0));

  getMealBadgeClass(type: string): string {
    switch (type) {
      case 'BREAKFAST':
        return 'badge-breakfast';
      case 'LUNCH':
        return 'badge-lunch';
      case 'DINNER':
        return 'badge-dinner';
      default:
        return 'badge-snack';
    }
  }
}
