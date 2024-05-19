import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { History } from '../models/History';

@Injectable({
  providedIn: 'root',
})
export class HistoryService {
  url: string = 'http://localhost:8080/transaction/history/all';
  temp: History = {} as History;
  urll: string = 'http://localhost:8080/transaction/history/id';
  id: string = '';

  constructor(private http: HttpClient, private apiService: ApiService) {}

  fetchData(): Observable<History[]> {
    if (typeof window !== 'undefined') {
    }
    const clientHeaders = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${window.localStorage.getItem('grn-tkn')}`,
    });
    return this.http.get<History[]>(this.url, { headers: clientHeaders });
  }

  setId(id: string) {
    this.id = id;
  }

  getId() {
    return this.id;
  }

  fetchDataById(id: string) {
    if (typeof window !== 'undefined') {
    }
    const clientHeaders = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${window.localStorage.getItem('grn-tkn')}`,
    });
    return this.http.post(
      `${this.urll}`,
      { id },
      {
        headers: clientHeaders,
      }
    );
  }
}
