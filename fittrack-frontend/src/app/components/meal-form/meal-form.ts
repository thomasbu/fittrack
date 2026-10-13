import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MealService } from '../../services/meal';
import { CreateMealDto, MealType } from '../../models/meal.model';
import { FoodItemService } from '../../services/food-item';
import { FoodItem } from '../../models/food-item.model';

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
  foodItems = signal<FoodItem[]>([]);

  // Types de repas pour le menu déroulant
  mealTypes = Object.values(MealType);

  // Structure du formulaire réactif
  mealForm: FormGroup = this.fb.group({
    userId: [1, [Validators.required]], // ID fixe à 1 pour les tests
    mealType: [MealType.BREAKFAST, [Validators.required]],
    notes: [''],
    isCheatMeal: [false],
    items: this.fb.array([]), // Liste dynamique d'aliments
  });

  constructor() {
    // Ajouter au moins un aliment par défaut au démarrage
    this.addItem();
    this.loadFoodItems();
  }

  // Getter pratique pour accéder au FormArray des aliments
  get items(): FormArray {
    return this.mealForm.get('items') as FormArray;
  }

  // Ajouter une ligne d'aliment dans le formulaire
  addItem(): void {
    const itemGroup = this.fb.group({
      foodItemId: [null, [Validators.required, Validators.min(1)]],
      quantityInGrams: [100, [Validators.required, Validators.min(1)]],
    });
    this.items.push(itemGroup);
  }

  // Supprimer une ligne d'aliment
  removeItem(index: number): void {
    if (this.items.length > 1) {
      this.items.removeAt(index);
    }
  }

  // Soumission du formulaire vers Spring Boot
  onSubmit(): void {
    if (this.mealForm.invalid) {
      this.mealForm.markAllAsTouched();
      return;
    }

    this.isSubmitting.set(true);
    this.errorMessage.set(null);

    const dto: CreateMealDto = this.mealForm.value;

    this.mealService.createMeal(dto).subscribe({
      next: (res) => {
        console.log('Repas créé avec succès !', res);
        this.isSubmitting.set(false);
        // Réinitialiser le formulaire
        this.mealForm.reset({
          userId: 1,
          mealType: MealType.BREAKFAST,
          notes: '',
          isCheatMeal: false,
        });
        this.items.clear();
        this.addItem();
      },
      error: (err) => {
        console.error('Erreur lors de la création :', err);
        this.errorMessage.set("Erreur lors de l'enregistrement du repas.");
        this.isSubmitting.set(false);
      },
    });
  }

  loadFoodItems(): void {
    this.foodItemService.getFoodItems().subscribe({
      next: (data) => {
        this.foodItems.set(data);
      },
      error: (err) => {
        console.error('Erreur API:', err);
      },
    });
  }
}
