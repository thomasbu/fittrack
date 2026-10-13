export interface CreateMealItemDto {
  foodItemId: number;
  quantityInGrams: number;
}

export interface MealItemResponseDto {
  id: number;
  foodItemId: number;
  foodName: string;
  quantityInGrams: number;
  calories: number;
  proteins: number;
  carbs: number;
  fat: number;
}
