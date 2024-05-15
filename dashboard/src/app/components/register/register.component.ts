import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { RouterTestingModule } from "@angular/router/testing";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [RouterLink, ReactiveFormsModule,],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  resp: any;

  constructor(
    private http: HttpClient,
    private router: Router,
    private toastr: ToastrService
  ) { }
  regist() {
    if(this.registerForm.valid){
      this.http.post('http://localhost:8080/auth/register', this.registerForm.value).subscribe({
        next: (data) => {
          this.resp = data;
          if(this.resp.statusCode == 201){
            this.router.navigate(['/login']);
            this.toastr.success('Registered successfully', 'Success');
          }
        },
        error: (error) => {
          if(error.status == 403){
            this.toastr.error("Data already exists", 'Error'); 
          }
        },
      })
    }
  
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
