import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { FoodItemService } from '../../services/food-item';

@Component({
  selector: 'app-food-item-form',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './food-item-form.html',
  styleUrl: './food-item-form.css',
})
export class FoodItemForm {
  private fb = inject(FormBuilder);
  private foodItemService = inject(FoodItemService);

  isOpen = signal(false);
  isSubmitting = signal(false);
  errorMessage = signal<string | null>(null);

  form = this.fb.nonNullable.group({
    name: ['', [Validators.required, Validators.maxLength(100)]],
    caloriesPer100gr: [0, [Validators.required, Validators.min(0)]],
    proteinPer100gr: [0, [Validators.required, Validators.min(0)]],
    carbsPer100gr: [0, [Validators.required, Validators.min(0)]],
    fatPer100gr: [0, [Validators.required, Validators.min(0)]],
  });

  toggle(): void {
    this.isOpen.update((open) => !open);
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.isSubmitting.set(true);
    this.errorMessage.set(null);

    this.foodItemService.createFoodItem({ userId: 1, ...this.form.getRawValue() }).subscribe({
      next: () => {
        this.form.reset();
        this.isSubmitting.set(false);
      },
      error: (err) => {
        console.error(err);
        this.errorMessage.set("Erreur lors de l'ajout de l'aliment.");
        this.isSubmitting.set(false);
      },
    });
  }
}
