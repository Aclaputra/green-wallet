import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  resp: any;
  url: string= "http://localhost:8080/user/profile";
  balance = '1000';
  bod = '01-01-1990';
  email = 'Cjg5N@example.com';
  isVerified = false;
  name = 'Clarke Jeffery';
  phone = '1234567890';
  poin = 0;

  constructor(
    private http: HttpClient
  ) {
    this.fetchData();
  }

  fetchData(){
    if(typeof window !== "undefined"){}
    const clientHeaders = new HttpHeaders({
      "Content-Type": "application/json",
      "Authorization": `Bearer ${window.localStorage.getItem("grn-tkn")}`
    });
    this.http.get(this.url, { headers: clientHeaders }).subscribe(
      (res)=>{
        console.log("This is from user service",res);
        this.resp=res;
        console.log(this.resp);
        this.balance=this.resp.data.balance;
        this.name=this.resp.data.name;
        this.email=this.resp.data.email;
        this.phone=this.resp.data.phoneNumber;
        this.bod=this.resp.data.birthDate;
      },
      (error)=>{
        console.error("Error fetch profile", error);
      }
    )
  }

  getBalance(){
    console.log("Trigger getBalance", this.resp)
    return this.balance;
  }

  getBirthDate(){
    return this.bod;
  }

  getEmail(){
    return this.email;
  }

  getVerified(){
    return this.isVerified;
  }

  getName(){
    return this.name;
  }

  getPhone(){
    return this.phone;
  }

  getPoin(){
    return this.poin;
  }
}
