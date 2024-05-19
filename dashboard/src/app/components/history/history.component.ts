import { Component, inject } from '@angular/core';
import { CardComponent } from '../card/card.component';
import { TestService } from '../../services/test.service';
import { HttpClient } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { HistoryService } from '../../services/history.service';
import { RouterLink } from '@angular/router';
import { CardListComponent } from '../card-list/card-list.component';

@Component({
  selector: 'app-history',
  standalone: true,
  imports: [CardComponent, DatePipe, RouterLink, CardListComponent],
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
          console.log('data', this.histories);
          console.log('test',this.histories.data[0].transDate);
          
        },
        error: (error)=>{
          console.error("Error fetch profile:", error);
        }
      }
    )
   }

}
