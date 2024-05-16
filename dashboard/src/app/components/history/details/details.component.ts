import { Component } from '@angular/core';
import { TypePipe } from '../../../pipes/type.pipe';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpClient, HttpHeaders } from '@angular/common/http';


@Component({
  selector: 'app-details',
  standalone: true,
  imports: [TypePipe],
  templateUrl: './details.component.html',
  styleUrl: './details.component.scss'
})
export class DetailsComponent {
  onConfirm() {
    this.modal.dismissAll();
  }
  url = "http://localhost:8080/transaction"
  histories:any

  constructor(private modal: NgbModal, private http: HttpClient) { }

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
        console.log('test',this.histories.data[0].transDetail);
      },
      (error)=>{
        console.error("Error fetch profile:", error);
      }
    )
  }

}
