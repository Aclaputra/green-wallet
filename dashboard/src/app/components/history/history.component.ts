import { Component, inject } from '@angular/core';
import { CardComponent } from '../card/card.component';
import { TestService } from '../../services/test.service';

@Component({
  selector: 'app-history',
  standalone: true,
  imports: [CardComponent],
  templateUrl: './history.component.html',
  styleUrl: './history.component.scss'
})
export class HistoryComponent {
  history:any[] = [
    
  ]

  test = inject(TestService)

  constructor() { 
    this.history = this.test.getProjects()
  }

}
