import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TestService {
  constructor(private http: HttpClient) { }

  getTestMessage(): Observable<string> {
    return this.http.get<string>('/api/courts/test', { responseType: 'text' as 'json' });
  }
}
