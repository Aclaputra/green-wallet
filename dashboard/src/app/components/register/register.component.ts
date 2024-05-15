import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  resp: any;

  constructor(
    private http: HttpClient,
    private router: Router
  ) { }
  regist() {
    this.http.post('http://localhost:8080/auth/register', this.registerForm.value).subscribe(
      (data) => {
        this.resp=data;
        if(this.resp.statusCode==200){
          this.router.navigate(['/dashboard']);
        }else{
          alert("Please input valid data!");
        }
    },
    (error)=>{
      alert("Please input valid data!");
      console.error(error);
    }
  )
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
