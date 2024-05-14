import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TopupComponent } from './components/topup/topup.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, TopupComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'dashboard';
}
