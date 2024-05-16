import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { LandingComponent } from '../landing/landing.component';

@Component({
  selector: 'app-auth-nav-n',
  standalone: true,
  imports: [RouterOutlet, RouterLink, LandingComponent],
  templateUrl: './auth-nav-n.component.html',
  styleUrl: './auth-nav-n.component.scss'
})
export class AuthNavNComponent {

}
