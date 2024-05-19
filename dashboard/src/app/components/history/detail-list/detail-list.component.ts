import { Component } from '@angular/core';
import { TypePipe } from '../../../pipes/type.pipe';
import { RpCurrencyPipe } from '../../../pipes/rp-currency.pipe';
import { DatePipe } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-detail-list',
  standalone: true,
  imports: [TypePipe, RpCurrencyPipe, DatePipe, FormsModule],
  templateUrl: './detail-list.component.html',
  styleUrl: './detail-list.component.scss'
})
export class DetailListComponent {
  histories: any;
  page: number = 0;
  size: number = 6;
  sortBy: string = "DESC";
  url: string= "";

  pageList: any[] = [];

  constructor(private http: HttpClient) {
    this.updateUrl();
    this.fetchData();
  }

  updateUrl() {
    this.url = `http://localhost:8080/transaction/history/page?page=${this.page}&size=${this.size}&sortBy=${this.sortBy}`;
  }

  fetchData() {
    this.histories={};
    this.pageList=[];
    const clientHeader = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${window.localStorage.getItem("grn-tkn")}`
    })
    this.http.get(this.url, { headers: clientHeader }).subscribe(
      (response) => {
        console.log(response);
        this.histories = response;
        this.pageList.push(...this.histories.data); // Append new data to pageList
      },
      (error) => {
        console.log(error);
      }
    )
  }

  next() {
    this.page++;
    this.updateUrl();
    console.log(this.pageList.length)
    this.fetchData();
  }

  previous() {
    if (this.page > 0) {
      this.page--;
      this.updateUrl();
      this.fetchData();
    }
  }

  handleSelection(value: string) {
    this.sortBy = value;
    this.updateUrl();
    this.fetchData();
  }
}
