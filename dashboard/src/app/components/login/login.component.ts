import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterLinkActive, RouterLink, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  email: string = "";
  url: string = 'http://localhost:8080/auth/login';
  username: string = '';
  password: string = '';
  token: string = '';
  resp: any;

  constructor(private httpClient: HttpClient, private router: Router, private toastr: ToastrService) {}

  login() {
    console.log(this.username, this.password);
    this.httpClient
      .post(this.url, {
        email: this.username.toString(),
        password: this.password.toString(),
      })
      .subscribe(
        (response) => {
          console.log(response);
          this.resp = response;
          this.token = this.resp.data.jwtToken;
          window.localStorage.setItem('grn-tkn', this.token);
          console.log(this.token);
          let loginTime = new Date().getTime();
          window.localStorage.setItem('tkn-exp', loginTime.toString());

          if (this.resp.statusCode == 200) {
            this.router.navigate(['auth/dashboard']);
            this.toastr.success('Login success!');
          }
        },
        (error) => {
          this.toastr.error('Login failed!');
        }
      );
  }
}
