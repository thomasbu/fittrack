export interface FoodItemResponseDto {
  id: number;
  userId: number;
  name: string;
  caloriesPer100gr: number;
  proteinPer100gr: number;
  carbsPer100gr: number;
  fatPer100gr: number;
}

// Le meme que FoodItemResponseDto mais sans l'id
export type CreateFoodItemDto = Omit<FoodItemResponseDto, 'id'>;
