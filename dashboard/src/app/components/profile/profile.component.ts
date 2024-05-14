import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent {
  opt: string|boolean = true;
  navigateToUpdateProfile() {
    this.opt = !this.opt
  }
  balance = '1000';
  name = 'Clarke Jeffery';
  email = 'Cjg5N@example.com';
  phone = '1234567890';
  bod = '01-01-1990';
}
