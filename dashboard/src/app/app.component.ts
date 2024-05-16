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

  constructor(
    private router: Router
  ){}

  ngOnInit(){
    if(typeof window !== "undefined"){
      if(window.localStorage.getItem("grn-tkn")){
        console.log("Token present");
        this.router.navigate(['/auth/dashboard']);
      }else{
        this.router.navigate(['/nauth/home']);
      }
    }
  }
}
