import { Component } from '@angular/core';
import { MealList } from './components/meal-list/meal-list';
import { MealForm } from './components/meal-form/meal-form';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [MealList, MealForm],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {}
