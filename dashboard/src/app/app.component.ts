import { Component } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { AuthNavComponent } from './components/auth-nav/auth-nav.component';
import { AuthNavNComponent } from './components/auth-nav-n/auth-nav-n.component';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, AuthNavComponent, AuthNavNComponent, NgIf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'dashboard';
  showAuthNavN: boolean = true;
  showAuthNav: boolean = false;
  // forgotPassUrl: string = "http://localhost:4200/greenpay/forgot-password?token=4fbae4ed-9eb0-49ff-a880-ee0d859326f8";
  // forgotPassUrl: string = window.location.href;

  constructor(
    private router: Router
  ){}

  ngOnInit(){
    // console.log(window.location.href=="http://localhost:4200/greenpay/home");
    // console.log(this.forgotPassUrl.substring(0,46)=="http://localhost:4200/greenpay/forgot-password");
    if(typeof window !== "undefined"){
      if(window.localStorage.getItem("grn-tkn")){
        console.log("Token present");
        this.router.navigate(['/account/dashboard']);
      }else if(window.location.href.substring(0,46)=="http://localhost:4200/greenpay/forgot-password"){
        let dataParam = window.location.href;
        this.router.navigate(['/reset-password'], {state: {data: dataParam}});
      }else{
        this.router.navigate(['/greenpay/home']);
      }
    }
  }
}
