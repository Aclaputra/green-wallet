import { Injectable } from '@angular/core';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root',
})
export class DashboardService {
  constructor(private apiService: ApiService) {}

  getDataTransaction = (url: string) => {
    return this.apiService.get(url, {
      headers: {
        Authorization: `Bearer ${window.localStorage.getItem('grn-tkn')}`,
      },
    });
  };
}
