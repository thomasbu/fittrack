import { CreateMealItemDto, MealItemResponseDto } from './meal-item.model';

export enum MealType {
  BREAKFAST = 'BREAKFAST',
  LUNCH = 'LUNCH',
  DINNER = 'DINNER',
  SNACK = 'SNACK',
}

export interface CreateMealDto {
  date: string; // Format ISO string: "YYYY-MM-DDTHH:mm:ss"
  mealType: MealType;
  isCheatMeal: boolean;
  notes?: string;
  userId: number;
  items: CreateMealItemDto[];
}

export interface MealResponseDto {
  id: number;
  date: string;
  mealType: MealType;
  isCheatMeal: boolean;
  notes?: string;
  userId: number;
  items: MealItemResponseDto[];
  totalCalories: number;
  totalProteins: number;
  totalCarbs: number;
  totalFat: number;
}
