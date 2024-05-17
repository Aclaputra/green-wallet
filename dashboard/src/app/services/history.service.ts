import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HistoryService {
  url: string= "http://localhost:8080/transaction/history/all";

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
