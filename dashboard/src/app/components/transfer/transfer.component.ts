import { Component } from '@angular/core';
import { TransferService } from '../../services/transfer.service';
import { error } from 'console';
import { FormsModule } from '@angular/forms';
import { CurrencyPipe } from '@angular/common';

@Component({
  selector: 'app-transfer',
  standalone: true,
  imports: [FormsModule, CurrencyPipe],
  templateUrl: './transfer.component.html',
  styleUrl: './transfer.component.scss',
})
export class TransferComponent {
  senderId!: string;
  receiverId!: string;
  amount: number = 0;

  constructor(private transferService: TransferService) {}

  transfer(): void {
    this.transferService
      .transferFunds(
        'http://localhost:8081/transfer',
        this.senderId,
        this.receiverId,
        this.amount
      )
      .subscribe({
        next: (data) => {
          console.log(data);
        },
        error: (error) => {
          console.error(error);
        },
      });
  }
}
