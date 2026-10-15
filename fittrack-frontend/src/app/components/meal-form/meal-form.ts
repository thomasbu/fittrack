import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MealService } from '../../services/meal';
import { CreateMealDto, MealType } from '../../models/meal.model';
import { CreateMealItemDto } from '../../models/meal-item.model';
import { FoodItemService } from '../../services/food-item';

@Component({
  selector: 'app-meal-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './meal-form.html',
  styleUrl: './meal-form.css',
})
export class MealForm {
  private fb = inject(FormBuilder);
  private mealService = inject(MealService);
  private foodItemService = inject(FoodItemService);

  isSubmitting = signal<boolean>(false);
  errorMessage = signal<string | null>(null);
  foodItems = this.foodItemService.foodItems;

  mealTypes = Object.values(MealType);

  mealForm: FormGroup = this.fb.group({
    userId: [1, [Validators.required]], // ID fixe à 1 pour les tests
    mealType: [MealType.BREAKFAST, [Validators.required]],
    notes: [''],
    cheatMeal: [false],
    items: this.fb.array([]),
  });

  constructor() {
    this.addItem();
    this.loadFoodItems();
  }

  get items(): FormArray {
    return this.mealForm.get('items') as FormArray;
  }

  // Calcul en direct des macros totales du repas en cours
  get totalMacros() {
    let calories = 0;
    let proteins = 0;
    let carbs = 0;
    let fat = 0;

    for (const control of this.items.controls) {
      const foodId = control.get('foodItemId')?.value;
      const quantity = control.get('quantityInGrams')?.value || 0;

      if (foodId) {
        const food = this.foodItems().find((f) => f.id === foodId);
        if (food) {
          const ratio = quantity / 100;
          calories += food.caloriesPer100gr * ratio;
          proteins += food.proteinPer100gr * ratio;
          carbs += food.carbsPer100gr * ratio;
          fat += food.fatPer100gr * ratio;
        }
      }
    }

    return {
      calories: Math.round(calories),
      proteins: Math.round(proteins * 10) / 10,
      carbs: Math.round(carbs * 10) / 10,
      fat: Math.round(fat * 10) / 10,
    };
  }

  addItem(): void {
    const itemGroup = this.fb.group({
      foodItemId: [null, [Validators.required]],
      quantityInGrams: [100, [Validators.required, Validators.min(1)]],
    });
    this.items.push(itemGroup);
  }

  removeItem(index: number): void {
    if (this.items.length > 1) {
      this.items.removeAt(index);
    }
  }

  onSubmit(): void {
    if (this.mealForm.invalid) {
      this.mealForm.markAllAsTouched();
      return;
    }

    this.isSubmitting.set(true);
    this.errorMessage.set(null);

    const raw = this.mealForm.getRawValue();
    const dto: CreateMealDto = {
      userId: raw.userId,
      mealType: raw.mealType,
      cheatMeal: !!raw.cheatMeal, // !! : jamais null, toujours true ou false
      notes: raw.notes,
      items: raw.items as CreateMealItemDto[],
    };

    console.log('Payload envoyé :', dto); // temporaire, pour vérifier

    this.mealService.createMeal(dto).subscribe({
      next: (res) => {
        console.log('Repas créé avec succès !', res);
        this.isSubmitting.set(false);
        this.resetForm();
        this.mealService.triggerRefresh();
      },
      error: (err) => {
        console.error('Erreur lors de la création :', err);
        this.errorMessage.set("Erreur lors de l'enregistrement du repas.");
        this.isSubmitting.set(false);
      },
    });
  }

  // Retour à l'état de départ : champs de base par défaut + une seule ligne d'aliment vide
  private resetForm(): void {
    this.items.clear();
    this.mealForm.reset({
      userId: 1,
      mealType: MealType.BREAKFAST,
      notes: '',
      cheatMeal: false,
    });
    this.addItem();
  }

  // Le service remplit lui-même le signal foodItems (via tap), on ne s'abonne que pour les erreurs
  loadFoodItems(): void {
    this.foodItemService.getFoodItems().subscribe({
      error: (err) => {
        console.error('Erreur API:', err);
        this.errorMessage.set('Impossible de charger la liste des aliments.');
      },
    });
  }
}
