import { NgIf } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [RouterLink, ReactiveFormsModule, NgIf],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  resp: any;
  myDate: string= "";
  dateFormat: string=this.myDate.toString().substring(8,10)+"-"+this.myDate.toString().substring(5,7)+"-"+this.myDate.toString().substring(0,4);
  data: any;

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
            this.router.navigate(['/nauth/login']);
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

  test(){
    console.log(this.myDate)
    console.log(this.dateFormat)
    this.data=this.registerForm.value
    console.log(this.data)
    this.data.birthDate=this.dateFormat
    console.log(this.data)
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
