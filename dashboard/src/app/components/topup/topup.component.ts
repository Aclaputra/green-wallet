import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-topup',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './topup.component.html',
  styleUrl: './topup.component.scss'
})
export class TopupComponent {
  merchants= {
    data: [
      {
        name: "Indomaret",
        nomor: "2348 9 9843 8423 8492",
        image: "../../../assets/indomaret.png"
      },
      {
        name: "Alfamart",
        nomor: "7823 6 2618 3764 9837",
        image: "../../../assets/alfamart.png"
      },
      {
        name: "DanDan",
        nomor: "9824 7 8918 9813 7634",
        image: "../../../assets/dandan.png"
      },
      {
        name: "Alfamidi",
        nomor: "1234 6 8723 9871 8471",
        image: "../../../assets/alfamidi.png"
      },
      {
        name: "Lawson",
        nomor: "8713 7 7625 1273 9871",
        image: "../../../assets/lawson.png"
      }
    ]
  }

  resp: any;
  url: string= "http://localhost:8080/user/profile";
  balance = '1000';
  name = 'Clarke Jeffery';
  email = 'Cjg5N@example.com';
  phone = '1234567890';
  bod = '01-01-1990';

  maxSaldo: number = 20000000;
  currentSaldo: number = 0;

  @Input() merchant: any;

  copyText(text:string){
    navigator.clipboard.writeText(text).then(()=>{
      console.log("Copied");
    }, (err)=>{
      console.error("Failed to Copy")
    })
  }

  test(){}

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
          this.currentSaldo=this.resp.data.balance;
          this.name=this.resp.data.name;
          this.email=this.resp.data.email;
          this.phone=this.resp.data.phoneNumber;
          this.bod=this.resp.data.birthDate;
        },
        (error)=>{
          console.error("Error fetch profile:", error);
          if(error.status==403){
            window.localStorage.clear();
          }
        }
      )
    }
  }
}
