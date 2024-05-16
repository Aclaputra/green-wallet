import { DatePipe } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [FormsModule, DatePipe],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent {
  opt: string|boolean = true;
  navigateToUpdateProfile() {
    this.opt = !this.opt
  }
  resp: any;
  url: string= "http://localhost:8080/user/profile";
  balance = '1000';
  name = 'Clarke Jeffery';
  email = 'Cjg5N@example.com';
  phone = '1234567890';
  bod = '01-01-1990';

  constructor(
    private http: HttpClient
  ){}

  ngOnInit(){
    if(typeof window !== "undefined"){
      const clientHeaders = new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${window.localStorage.getItem("grn-tkn")}`
      });
      this.http.get(this.url, { headers: clientHeaders }).subscribe(
        (data)=>{
          console.log(data);
          this.resp=data;
          console.log(this.resp.statusCode)
          this.balance=this.resp.data.balance;
          this.name=this.resp.data.name;
          this.email=this.resp.data.email;
          this.phone=this.resp.data.phoneNumber;
          this.bod= (this.resp.data.birthDate).toString().slice(0,10);
        },
        (error)=>{
          console.error("Error fetch profile:", error);
        }
      )
    }
  }
}
