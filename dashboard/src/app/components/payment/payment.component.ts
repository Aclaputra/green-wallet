import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './payment.component.html',
  styleUrl: './payment.component.scss'
})
export class PaymentComponent {
  partnerCode: any;
  amount: number = 0;
  currentSaldo: number =120000;
  phoneNumber: number =0;
  info: string ="message";
  url: string = "http://localhost:8080/transaction/transfer";
  urlProfile: string= "http://localhost:8080/user/profile";
  resp: any;

  constructor(
    private http: HttpClient
  ){}

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
        if(error.status==403){
          window.localStorage.clear();
        }
      }
    )
  }

  pay(){
    console.log(this.partnerCode, this.amount, this.info)
  }
}
