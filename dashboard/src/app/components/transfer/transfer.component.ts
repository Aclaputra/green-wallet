import { Component } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import { RpCurrencyPipe } from '../../pipes/rp-currency.pipe';
import { NgxCurrencyDirective } from 'ngx-currency';
import { NgxIntlTelInputModule } from 'ngx-intl-tel-input-gg';

@Component({
  selector: 'app-transfer',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    RpCurrencyPipe,
    NgxCurrencyDirective,
    NgxIntlTelInputModule,
  ],
  templateUrl: './transfer.component.html',
  styleUrl: './transfer.component.scss',
})
export class TransferComponent {
  senderId!: string;
  receiverId!: string;
  amount: number = 0;
  currentSaldo!: number;
  phoneNumber: string = '';
  info: string = 'message';
  url: string = 'http://localhost:8080/transaction/transfer';
  urlProfile: string = 'http://localhost:8080/user/profile';
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

  transfer(): void {
    console.log(this.amount, this.currentSaldo, this.phoneNumber);
    const clientHeaders = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${window.localStorage.getItem('grn-tkn')}`,
    });

    const clientBody = {
      destination: this.phoneNumber,
      amount: this.amount.toString(),
      message: this.info,
    };

    if (this.phoneNumber == this.resp.data.phoneNumber) {
      alert('Your just input other phone member');
    } else {
      this.http
        .post(this.url, clientBody, { headers: clientHeaders })
        .subscribe(
          (response) => {
            console.log(response);
            this.toastr.success(
              `Success transfer Rp${clientBody.amount} to ${clientBody.destination}`,
              'Success'
            );
            setInterval(() => {
              location.reload();
            }, 2000);
          },
          (error) => {
            console.error('Error transfer:', error);
            this.toastr.error(
              'Make sure your phone number is member!',
              'Error'
            );
            setInterval(() => {
              location.reload();
            }, 2000);
          }
        );
    }
  }
}
