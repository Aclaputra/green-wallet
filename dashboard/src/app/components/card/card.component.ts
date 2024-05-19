import { Component, Input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { NgbModal, NgbDate, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { DetailsComponent } from '../history/details/details.component';
import { History } from '../../models/History';
import { DatePipe, JsonPipe } from '@angular/common';
import { HistoryService } from '../../services/history.service';

@Component({
  selector: 'app-card',
  standalone: true,
  imports: [RouterLink, DetailsComponent, DatePipe, JsonPipe],
  templateUrl: './card.component.html',
  styleUrl: './card.component.scss',
})
export class CardComponent {
  @Input() item!: History;
  constructor(private modal: NgbModal, private history: HistoryService) {}

  open() {
    const modalRef = this.modal.open(DetailsComponent);
    modalRef.componentInstance.project = this.item;
    this.history.setId(this.item.id);
    console.info(this.item.id);
  }
}
