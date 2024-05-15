import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { RouterTestingModule } from "@angular/router/testing";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [RouterLink, ReactiveFormsModule, RouterTestingModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  regist() {  
    console.log(this.registerForm.value);
  }

  registerForm = new FormGroup({
    email: new FormControl('', [Validators.email, Validators.required]),
    password: new FormControl('', [Validators.required]),
    name: new FormControl('', [Validators.required]),
    phoneNumber: new FormControl('', [Validators.required]),
    birthDate: new FormControl('', [Validators.required]),
    username: new FormControl('', [Validators.required]),
  })


}
