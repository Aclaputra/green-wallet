import { Injectable } from '@angular/core';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root',
})
export class PlantDataService {
  constructor(private apiService: ApiService) {}

  getPlantData = (url: string) => {
    return this.apiService.get(url);
  };
}
