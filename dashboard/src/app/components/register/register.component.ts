import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { RegisterService } from '../../services/register.service';
import { RouterTestingModule } from '@angular/router/testing';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [RouterLink, ReactiveFormsModule,RouterTestingModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  resp: any;

  constructor(
    private register: RegisterService,
    private router: Router, 
    private toastr: ToastrService
  ) { }
  regist() {
    if(this.registerForm.valid){
      this.register.registUser(this.registerForm.value).subscribe({
        next: (data) => {
          this.resp = data
          if (this.resp.statusCode == 201) {
            this.router.navigate(['/nauth/login']);
            this.toastr.success('Register success!');
          }
        },
        error: (error) => {
          this.toastr.error('Register failed!');
        }
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

// 