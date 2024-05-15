import { Component } from '@angular/core';
import { TransferService } from '../../services/transfer.service';
import { FormsModule } from '@angular/forms';
import { CurrencyPipe } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';

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
  currentSaldo: number =120000;
  phoneNumber: number =0;
  info: string ="message";
  url: string = "http://localhost:8080/transaction/transfer";

  constructor(
    private transferService: TransferService,
    private http: HttpClient
  ) {}

  transfer(): void {
    console.log(this.amount, this.currentSaldo, this.phoneNumber)
    const clientHeaders = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${window.localStorage.getItem("grn-tkn")}`
    });

    const clientBody ={
      destination: this.phoneNumber.toString(),
      amount: this.amount.toString(),
      description: this.info
  }

    this.http.post(this.url, clientBody, {headers: clientHeaders}).subscribe(
      (response)=>{
        console.log(response);
      },
      (error)=>{
        console.error("Error transfer:", error);
      }
    )
    // this.transferService
    //   .transferFunds(
    //     'http://localhost:8081/transfer',
    //     this.senderId,
    //     this.receiverId,
    //     this.amount
    //   )
    //   .subscribe({
    //     next: (data) => {
    //       console.log(data);
    //     },
    //     error: (error) => {
    //       console.error(error);
    //     },
    //   });
  }
}
