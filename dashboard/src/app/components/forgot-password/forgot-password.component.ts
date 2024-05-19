import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-forgot-password',
  standalone: true,
  imports: [RouterLink, FormsModule],
  templateUrl: './forgot-password.component.html',
  styleUrl: './forgot-password.component.scss',
})
export class ForgotPasswordComponent {
  email: string ="";
  url: string = "http://localhost:8080/auth/reset";

  constructor(
    private http: HttpClient,
    private toastr: ToastrService,
    private router: Router
  ){}

  onResetPassword() {
    console.log('reset password', this.email);
    this.http.post(this.url, {
      id: this.email
    }).subscribe(
      (response)=>{
        console.log(response);
        this.toastr.success("Check your email inbox", "Success");
        setInterval(()=>{
          this.router.navigate(['/greenpay/home']);
        }, 2000)
      },
      (error)=>{
        console.log(error);
        this.toastr.error("Failed, makesure email is member", "Failed");
        setInterval(()=>{
          this.router.navigate(['/greenpay/home']);
        }, 2000)
      }
    )
  }
}
