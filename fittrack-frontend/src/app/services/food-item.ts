import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Observable } from 'rxjs';
import { FoodItem } from '../models/food-item.model';

@Injectable({
  providedIn: 'root',
})
export class FoodItemService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/food-items`;

  // Récupérer tous les repas d'un utilisateur
  getFoodItems(): Observable<FoodItem[]> {
    return this.http.get<FoodItem[]>(this.apiUrl);
  }
}
