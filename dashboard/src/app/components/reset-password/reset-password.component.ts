import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-reset-password',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './reset-password.component.html',
  styleUrl: './reset-password.component.scss'
})
export class ResetPasswordComponent {
  dataRouter: any;
  url: string="http://localhost:8080/auth/user/reset-password";
  token: string="";
  email: string="";
  newPassword: string="";

  constructor(
    private router: Router,
    private http: HttpClient,
    private toastr: ToastrService
  ){
    console.log("Data param from constructor:", this.router.getCurrentNavigation()?.extras.state)
    this.dataRouter=this.router.getCurrentNavigation()?.extras.state;
    let splitData = (this.dataRouter.data.substring(47, this.dataRouter.data.length)).split("&");
    this.token=splitData[0].substring(6,splitData[0].length);
    this.email=splitData[1].substring(6,splitData[1].length);
    console.log(this.token, this.email);
  }

  onResetPassword(){
    console.log(this.email, this.newPassword, this.token);
    const clientHeader = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.token}`
    })
    this.http.post(this.url, {
      email: this.email,
      password: this.newPassword,
      token: this.token
    }, {headers: clientHeader}).subscribe(
      (response)=>{
        console.log(response);
        this.toastr.success("You change the password", "Success");
        setInterval(()=>{
          this.router.navigate(['/greenpay/login']);
        }, 2000);
      },
      (error)=>{
        console.log(error);
        this.toastr.error("Failed reset password", "Failed");
        setInterval(()=>{
          this.router.navigate(['/greenpay/register'])
        }, 2000)
      }
    )
  }
}
