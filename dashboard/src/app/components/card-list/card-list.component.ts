import { Component, Input } from '@angular/core';
import { DetailListComponent } from '../history/detail-list/detail-list.component';
import { RouterLink } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-card-list',
  standalone: true,
  imports: [DetailListComponent, RouterLink],
  templateUrl: './card-list.component.html',
  styleUrl: './card-list.component.scss'
})
export class CardListComponent {
  @Input() item!: History;
  constructor(private modal: NgbModal, private http: HttpClient) {}

  open(){
    console.log("Open")
    const modalRef = this.modal.open(DetailListComponent);
    modalRef.componentInstance.project = this.item;
  }

}
