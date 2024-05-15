import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterLinkActive, RouterLink, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  url: string = "http://localhost:8080/auth/login";
  username: string = "";
  password: string = "";
  token: string = "";
  resp: any ;

  constructor(
    private httpClient: HttpClient,
    private router: Router
  ){}

  login() {
    this.httpClient.post(this.url, { 
      email: this.username.toString(),
      password: this.password.toString()
     }).subscribe(
      (response) => {
        this.resp=response;
        this.token=this.resp.data.jwtToken;
        window.localStorage.setItem("grn-tkn", this.token);
        console.log(this.token);
        let loginTime = new Date().getTime();
        window.localStorage.setItem('tkn-exp', loginTime.toString());

        if(this.resp.statusCode==200){
          this.router.navigate(['/dashboard']);
        }else{
          alert("Please input valid credential!");
        }
      },
      (error) => {
        alert("Please input valid credential!");
        console.error("Error", error);
      }
     )
  }

}
