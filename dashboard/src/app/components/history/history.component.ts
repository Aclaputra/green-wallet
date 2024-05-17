import { Component, inject } from '@angular/core';
import { CardComponent } from '../card/card.component';
import { TestService } from '../../services/test.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { HistoryService } from '../../services/history.service';

@Component({
  selector: 'app-history',
  standalone: true,
  imports: [CardComponent, DatePipe],
  templateUrl: './history.component.html',
  styleUrl: './history.component.scss'
})
export class HistoryComponent {
  histories:any

  test = inject(TestService)
  http = inject(HttpClient)
  

  constructor(private history: HistoryService) {
    this.history.fetchData().subscribe(
      {
        next: (data?)=>{
          this.histories=data
        },
        error: (error)=>{
          console.error("Error fetch profile:", error);
        }
      }
    )
   }

}
