import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Observable, tap } from 'rxjs';
import { FoodItemResponseDto, CreateFoodItemDto } from '../models/food-item.model';

@Injectable({
  providedIn: 'root',
})
export class FoodItemService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/food-items`;

  // Liste partagée : les composants qui la lisent se mettent à jour automatiquement
  readonly foodItems = signal<FoodItemResponseDto[]>([]);

  getFoodItems(): Observable<FoodItemResponseDto[]> {
    return this.http
      .get<FoodItemResponseDto[]>(this.apiUrl)
      .pipe(tap((data) => this.foodItems.set(data)));
  }

  createFoodItem(dto: CreateFoodItemDto): Observable<FoodItemResponseDto> {
    return this.http
      .post<FoodItemResponseDto>(this.apiUrl, dto)
      .pipe(tap((created) => this.foodItems.update((list) => [...list, created])));
  }
}
