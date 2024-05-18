import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RpCurrencyPipe } from '../../pipes/rp-currency.pipe';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [FormsModule, RpCurrencyPipe],
  templateUrl: './payment.component.html',
  styleUrl: './payment.component.scss',
})
export class PaymentComponent {
  merchantName: string = '';
  amount: number = 0;
  currentSaldo: number = 120000;
  phoneNumber: number = 0;
  info: string = 'message';
  url: string = 'http://localhost:8080/transaction/transfer';
  urlProfile: string = 'http://localhost:8080/user/profile';
  urlPayment: string = 'http://localhost:8080/transaction/payment';
  resp: any;

  constructor(private http: HttpClient, private toastr: ToastrService) {}

  ngOnInit() {
    if (typeof window !== 'undefined') {
      this.getData();
    }
  }

  getData() {
    const clientHeaders = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${window.localStorage.getItem('grn-tkn')}`,
    });
    this.http.get(this.urlProfile, { headers: clientHeaders }).subscribe(
      (data) => {
        this.resp = data;
        this.currentSaldo = this.resp.data.balance;
      },
      (error) => {
        console.error('Error fetch profile:', error);
        if (error.status == 403) {
          window.localStorage.clear();
        }
      }
    );
  }

  pay() {
    console.log(this.merchantName, this.amount, this.info);
    if (typeof window !== 'undefined') {
      const clientHeaders = new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${window.localStorage.getItem('grn-tkn')}`,
      });
      this.http
        .post(
          this.urlPayment,
          {
            destination: this.merchantName,
            amount: this.amount,
            message: this.info,
          },
          { headers: clientHeaders }
        )
        .subscribe(
          (response) => {
            this.resp = {};
            this.resp = response;
            console.log(response);
            console.log(this.resp.statusCode);
            if (this.resp.statusCode == 500) {
              this.toastr.error('Failed to pay, check merchant!');
            }
            if (this.resp.statusCode == 200) {
              this.toastr.success(
                `Payment via ${this.merchantName} for IDR${this.amount}`,
                'Success'
              );
            }

            setInterval(() => {
              location.reload();
            }, 2000);
          },
          (error) => {
            this.toastr.error('Failed to pay, check merchant!');
            console.log(error);
          }
        );
    }
  }
}
