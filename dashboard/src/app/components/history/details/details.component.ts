import { Component, Input } from '@angular/core';
import { TypePipe } from '../../../pipes/type.pipe';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HistoryService } from '../../../services/history.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-details',
  standalone: true,
  imports: [TypePipe, DatePipe],
  templateUrl: './details.component.html',
  styleUrl: './details.component.scss',
})
export class DetailsComponent {
  transType!: string;
  class: any;
  onConfirm() {
    this.modal.dismissAll();
  }
  url = 'http://localhost:8080/transaction';
  histories: any;
  id: string = '';
  desc: string = '';
  amount: number = 0;
  targetName: string = '';
  date!: any;

  constructor(
    private modal: NgbModal,
    private http: HttpClient,
    private historyS: HistoryService
  ) {}

  ngOnInit() {
    this.id = this.historyS.getId();
    console.log('lala', this.id);

    this.historyS.fetchDataById(this.id).subscribe({
      next: (data) => {
        this.histories = data;
        this.date = new DatePipe('en-US').transform(this.histories.data.transDate, 'MM/dd/yyyy, h:mm a');
        this.desc = this.histories.data.message;
        this.amount = this.histories.data.amount;
        this.transType = this.histories.data.transType;
        this.targetName = this.histories.data.targetName;
        
        if (this.transType == 'TOP_UP') {
          this.class = 'alert alert-primary';
        } else if (this.transType == 'TRANSFER') {
          this.class = 'alert alert-danger';
        } else if (this.transType == 'PAYMENT') {
          this.class = 'alert alert-success'
        }
      },
      error: (error) => {
        console.error('Error fetch profile:', error);
      },
    });
  }
}
