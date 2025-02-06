import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Utilisateur } from '../models/utilisateur';

@Injectable({
  providedIn: 'root'
})
export class UtilisateurService {
  private apiUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) {}

  register(utilisateur: Utilisateur): Observable<any> {
    console.log("Sending registration request: ", utilisateur);
    return this.http.post<any>(`${this.apiUrl}/register`, utilisateur, {
      headers: this.getHeaders(),
      responseType: 'text' as 'json'
    }).pipe(
      catchError(this.handleError)
    );
  }

  login(credentials: { username: string; password: string }): Observable<any> {
    console.log("Sending login request: ", credentials);
    return this.http.post<any>(`${this.apiUrl}/login`, credentials, {
      headers: this.getHeaders(),
      responseType: 'text' as 'json'
    }).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'Something went wrong; please try again later.';
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred.
      errorMessage = `Client-side error: ${error.error.message}`;
    } else {
      // The backend returned an unsuccessful response code.
      errorMessage = `Server-side error: ${error.status} ${error.message}`;
      console.error('Server response:', error.error);
    }
    console.error('An error occurred:', errorMessage);
    return throwError(errorMessage);
  }

  private getHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Content-Type': 'application/json'
    });
  }

  getUtilisateurs(): Observable<Utilisateur[]> {
    return this.http.get<Utilisateur[]>(this.apiUrl)
      .pipe(
        catchError(this.handleError)
      );
  }

  getUtilisateur(id: number): Observable<Utilisateur> {
    return this.http.get<Utilisateur>(`${this.apiUrl}/${id}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  addUtilisateur(utilisateur: Utilisateur): Observable<Utilisateur> {
    return this.http.post<Utilisateur>(this.apiUrl, utilisateur, { headers: this.getHeaders() })
      .pipe(
        catchError(this.handleError)
      );
  }

  updateUtilisateur(id: number, utilisateur: Utilisateur): Observable<Utilisateur> {
    return this.http.put<Utilisateur>(`${this.apiUrl}/${id}`, utilisateur, { headers: this.getHeaders() })
      .pipe(
        catchError(this.handleError)
      );
  }

  deleteUtilisateur(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() })
      .pipe(
        catchError(this.handleError)
      );
  }
}
