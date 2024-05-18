import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { RegisterService } from '../../services/register.service';
import { DatePipe } from '@angular/common';
//import { RouterTestingModule } from '@angular/router/testing';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [RouterLink, ReactiveFormsModule,DatePipe],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  resp: any;
  bod!:any
  email:string | null | undefined = ''
  name:string | null | undefined = ''
  password: string | null | undefined = ''
  phoneNumber:string | null | undefined = ''
  username:string | null | undefined = ''


  constructor(
    private register: RegisterService,
    private router: Router, 
    private toastr: ToastrService
  ) { }
  regist() {
    if(this.registerForm.valid){
      this.bod = new DatePipe('en-US').transform(this.registerForm.value.birthDate, 'dd-MM-yyy');
      this.name = this.registerForm.value.name
      this.username = this.registerForm.value.username
      this.password = this.registerForm.value.password
      this.phoneNumber = this.registerForm.value.phoneNumber
      this.email = this.registerForm.value.email

      let dataReg= {
        email:  this.email,
        name: this.name,
        username: this.username,
        password:this.password,
        birthDate: this.bod,
        phoneNumber:this.phoneNumber
      }
      console.log(dataReg);
      

      
      this.register.registUser(
      dataReg).subscribe({
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
