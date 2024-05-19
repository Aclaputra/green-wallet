import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ChartModule } from 'primeng/chart';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { DashboardService } from '../../services/dashboard.service';
import { RpCurrencyPipe } from '../../pipes/rp-currency.pipe';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    RouterLink,
    CommonModule,
    ChartModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    RpCurrencyPipe,
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent {
  // data sum for line chart
  data: any;
  options: any;
  incomeDay: string[] = Array(7).fill('');
  outcomeDay: string[] = Array(7).fill('');

  monthName!: string;

  // count
  totalTransactionPerWeek!: number;

  // sum
  totalIncomePermonth!: number;
  totalOutcomePermonth!: number;

  constructor(private dashboard: DashboardService) {}

  getCountTrans() {
    this.dashboard
      .getDataTransaction('http://localhost:8080/transaction/all/count')
      .subscribe({
        next: (resp: any) => {
          this.totalTransactionPerWeek = resp.data[7];
        },
        error: (err) => {
          console.error(err);
        },
      });
  }

  getSumTrans() {
    this.dashboard
      .getDataTransaction('http://localhost:8080/transaction/all/sum')
      .subscribe({
        next: (resp: any) => {
          // sum per week
          for (let i = 0; i < 7; i++) {
            this.incomeDay[i] = resp.data[0][i];
            this.outcomeDay[i] = resp.data[1][i];
          }
          // sum total permont
          this.totalIncomePermonth = resp.data[0][8];
          this.totalOutcomePermonth = resp.data[1][8];
        },
        error: (err) => {
          console.error(err);
        },
      });
  }

  ngOnInit() {
    const today = new Date();
    this.monthName = today.toLocaleString('default', { month: 'long' });

    this.getCountTrans();
    this.getSumTrans();

    const textColor = '#333'; // hitam
    const textColorSecondary = '#666'; // abu2
    const surfaceBorder = '#ccc'; // abu2 muda

    this.data = {
      labels: [
        'Sunday',
        'Monday',
        'Tuesday',
        'Wednesday',
        'Thursday',
        'Friday',
        'Saturday',
      ],
      datasets: [
        {
          label: 'Income',
          backgroundColor: '#198b0e',
          borderColor: '#198b0e',
          data: this.incomeDay,
        },
        {
          label: 'Outcome',
          backgroundColor: '#9c1c13',
          borderColor: '#9c1c13',
          data: this.outcomeDay,
        },
      ],
    };
    this.options = {
      maintainAspectRatio: false,
      aspectRatio: 0.8,
      locale: 'id-ID',
      plugins: {
        legend: {
          labels: {
            color: textColor,
          },
        },
      },
      scales: {
        x: {
          ticks: {
            color: textColorSecondary,
            font: {
              weight: 500,
            },
          },
          grid: {
            color: surfaceBorder,
            drawBorder: false,
          },
        },
        y: {
          ticks: {
            callback: (value: number, index: number, values: number[]) => {
              return new Intl.NumberFormat('id-ID', {
                style: 'currency',
                currency: 'IDR',
                minimumFractionDigits: 0,
                maximumFractionDigits: 0,
              }).format(value);
            },
            color: textColorSecondary,
          },
          grid: {
            color: surfaceBorder,
            drawBorder: false,
          },
        },
      },
    };
  }
}
