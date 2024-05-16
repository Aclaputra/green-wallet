import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root',
})
export class TransferService {
  constructor(private apiService: ApiService) {}

  transferFunds(
    url: string,
    senderId: string,
    receiverId: string,
    amount: number
  ): Observable<any> {
    return this.apiService.post(url, { senderId, receiverId, amount });
  }
}
