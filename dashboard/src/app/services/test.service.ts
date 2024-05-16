import { Injectable } from '@angular/core';
import { History } from '../models/History';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class TestService {
  url = 'http://localhost:8080/transaction'

  constructor(private http: HttpClient) { }

  fetchData(): Observable<History[]> {
    if(typeof window !== "undefined"){}
    const clientHeaders = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${window.localStorage.getItem("grn-tkn")}`
    });
    return this.http.get<History[]>(this.url, { headers: clientHeaders });
  }
}