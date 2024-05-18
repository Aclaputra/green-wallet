import { DatePipe } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { RpCurrencyPipe } from '../../pipes/rp-currency.pipe';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [ReactiveFormsModule, DatePipe, FormsModule, RpCurrencyPipe],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss',
})
export class ProfileComponent {
  test() {}
  opt: string | boolean = true;
  navigateToUpdateProfile() {
    this.opt = !this.opt;
  }
  resp: any;
  url: string = 'http://localhost:8080/user/profile';
  balance: number = 0;
  name = '';
  email = '';
  phone = '';
  bod = '';
  img = '';

  constructor(private http: HttpClient) {}

  ngOnInit() {
    if (typeof window !== 'undefined') {
      const clientHeaders = new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${window.localStorage.getItem('grn-tkn')}`,
      });
      this.http.get(this.url, { headers: clientHeaders }).subscribe(
        (data) => {
          console.log('haha', data);
          this.resp = data;
          console.log('uang', this.resp.data.balance);

          console.log(this.resp.statusCode);
          this.balance = this.resp.data.balance;
          console.log('blens', this.balance);

          this.name = this.resp.data.name;
          this.email = this.resp.data.email;
          this.phone = this.resp.data.phoneNumber;
          this.bod = this.resp.data.birthDate.toString().slice(0, 10);
          this.img = this.resp.data.profileImageUrl;
        },
        (error) => {
          console.error('Error fetch profile:', error);
          if (error.status == 403) {
            window.localStorage.clear();
          }
        }
      );
    }
  }
}
