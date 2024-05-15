import { Component, Input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { NgbModal, NgbDate, NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { DetailsComponent } from '../history/details/details.component';
import { History } from '../../models/History';

@Component({
  selector: 'app-card',
  standalone: true,
  imports: [RouterLink, DetailsComponent],
  templateUrl: './card.component.html',
  styleUrl: './card.component.scss'
})
export class CardComponent {
  constructor(private modal: NgbModal) { }
  open() {
    const modalRef = this.modal.open(DetailsComponent);
    modalRef.componentInstance.project = this.project;
  }
  @Input() project = {} as History;

}
