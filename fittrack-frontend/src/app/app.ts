import { Component } from '@angular/core';
import { MealList } from './components/meal-list/meal-list';
import { MealForm } from './components/meal-form/meal-form';
import { FoodItemForm } from './components/food-item-form/food-item-form';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [MealList, MealForm, FoodItemForm],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {}
