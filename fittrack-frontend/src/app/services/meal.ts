import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject, tap } from 'rxjs';
import { MealResponseDto, CreateMealDto } from '../models/meal.model';

@Injectable({
  providedIn: 'root',
})
export class MealService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/meals';

  // Un Subject simple pour déclencher les rafraîchissements
  private refreshSubject = new Subject<void>();
  mealRefresh$ = this.refreshSubject.asObservable();

  getMealsByUserId(userId: number): Observable<MealResponseDto[]> {
    console.log('📡 Appel HTTP GET vers le backend en cours...');
    return this.http
      .get<MealResponseDto[]>(`${this.apiUrl}/user/${userId}`)
      .pipe(tap((meals) => console.log('✅ Repas reçus du backend :', meals)));
  }

  createMeal(mealDto: CreateMealDto): Observable<MealResponseDto> {
    return this.http.post<MealResponseDto>(this.apiUrl, mealDto).pipe(
      tap(() => {
        this.triggerRefresh();
      })
    );
  }

  triggerRefresh(): void {
    console.log('🔄 Trigger refresh appelé dans le service !');
    this.refreshSubject.next();
  }

  deleteMeal(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
