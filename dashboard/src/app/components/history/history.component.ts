import { Component, inject } from '@angular/core';
import { CardComponent } from '../card/card.component';
import { TestService } from '../../services/test.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-history',
  standalone: true,
  imports: [CardComponent, DatePipe],
  templateUrl: './history.component.html',
  styleUrl: './history.component.scss'
})
export class HistoryComponent {
  
  histories:any
  url: string= "http://localhost:8080/transaction";

  test = inject(TestService)
  http = inject(HttpClient)

  ngOnInit(){
    if(typeof window !== "undefined"){}
    const clientHeaders = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${window.localStorage.getItem("grn-tkn")}`
    });
    this.http.get(this.url, { headers: clientHeaders }).subscribe(
      (data)=>{
        this.histories=data
        console.log(this.histories);
        console.log(this.histories.data[0].transDetail);
      },
      (error)=>{
        console.error("Error fetch profile:", error);
      }
    )
  }
}
