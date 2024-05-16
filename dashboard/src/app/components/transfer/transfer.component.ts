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
  urlProfile: string= "http://localhost:8080/user/profile";
  resp: any;

  constructor(
    private transferService: TransferService,
    private http: HttpClient
  ) {}

  ngOnInit(){
    if(typeof window !== "undefined"){
      this.getData();
    }
  }

  getData(){
    const clientHeaders = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${window.localStorage.getItem("grn-tkn")}`
    });
    this.http.get(this.urlProfile, { headers: clientHeaders }).subscribe(
      (data)=>{
        this.resp=data;
        this.currentSaldo=this.resp.data.balance
      },
      (error)=>{
        console.error("Error fetch profile:", error);
      }
    )
  }

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

    if(this.phoneNumber==this.resp.data.phoneNumber){
      alert("Your just input other phone member");
    }else{
      this.http.post(this.url, clientBody, {headers: clientHeaders}).subscribe(
        (response)=>{
          console.log(response);
          alert("Transfer success!");
        },
        (error)=>{
          console.error("Error transfer:", error);
        }
      )
    }
  }
}
