import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { toObservable } from '@angular/core/rxjs-interop';
import { MealResponseDto, CreateMealDto } from '../models/meal.model';

@Injectable({
  providedIn: 'root',
})
export class MealService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/meals'; // Adapte ton URL si besoin

  // Signal déclencheur global pour notifier les changements
  private refreshSource = signal<number>(0);
  mealRefresh$ = toObservable(this.refreshSource);

  getMealsByUserId(userId: number): Observable<MealResponseDto[]> {
    return this.http.get<MealResponseDto[]>(`${this.apiUrl}/user/${userId}`);
  }

  createMeal(mealDto: CreateMealDto): Observable<MealResponseDto> {
    return this.http.post<MealResponseDto>(this.apiUrl, mealDto).pipe(
      tap(() => {
        // Incrémente le compteur à chaque création réussie pour forcer le rechargement
        this.refreshSource.update((val) => val + 1);
      })
    );
  }

  triggerRefresh(): void {
    this.refreshSource.update((val) => val + 1);
  }
}
