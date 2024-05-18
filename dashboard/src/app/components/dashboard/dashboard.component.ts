import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { ChartModule } from 'primeng/chart';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { DashboardService } from '../../services/dashboard.service';
import { RpCurrencyPipe } from '../../pipes/rp-currency.pipe';

// interface Transaction {
//   transDetail: {
//     created_at: string;
//     type: string;
//     amount: number;
//   }
// }

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
  // for line chart
  data: any;
  options: any;

  // for card
  monthName!: string;
  incomePerMonth!: number;
  outcomePerMonth!: number;
  currentMonth!: number;
  previousMonth!: number;

  constructor(private dashboard: DashboardService) {}

  getDataForChart() {
    this.dashboard
      .getDataTransaction('http://localhost:8080/transaction/history/all')
      .subscribe({
        next: (resp: any) => {
          this.processTransactionPerWeek(resp.data);
          this.processReportPerMonth(resp.data);
        },
        error: (err) => {
          console.error(err);
        },
      });
  }

  // for line chart
  processTransactionPerWeek(transactions: any[]) {
    const dailyIncome = new Array(7).fill(Number(0));
    const dailyOutcome = new Array(7).fill(Number(0));

    const today = new Date();
    const oneDay = 24 * 60 * 60 * 1000; // in milliseconds
    const dayNames = [
      'Sunday',
      'Monday',
      'Tuesday',
      'Wednesday',
      'Thursday',
      'Friday',
      'Saturday',
    ];

    for (const transaction of transactions) {
      const transactionDate = new Date(transaction.transDate);
      const dayIndex = transactionDate.getDay();
      const daysAgo = Math.floor(
        (today.getTime() - transactionDate.getTime()) / oneDay
      );
      if (daysAgo < 7) {
        const amount = Number(transaction.amount);
        if (transaction.transType === 'TOP_UP') {
          console.info('Income: ' + dailyIncome[dayIndex]);
          dailyIncome[dayIndex] += amount;
        } else if (transaction.transType === 'TRANSFER') {
          console.info('Outcome:' + dailyOutcome[dayIndex]);
          dailyOutcome[dayIndex] += amount;
        } else if (transaction.transType === 'PAYMENT') {
          dailyOutcome[dayIndex] += amount;
        }
      }
    }

    this.data = {
      labels: [
        dayNames[0],
        dayNames[1],
        dayNames[2],
        dayNames[3],
        dayNames[4],
        dayNames[5],
        dayNames[6],
      ],
      datasets: [
        {
          label: 'Income',
          backgroundColor: '#198b0e',
          borderColor: '#198b0e',
          data: dailyIncome.map((value) => Number(value)),
        },
        {
          label: 'Outcome',
          backgroundColor: '#9c1c13',
          borderColor: '#9c1c13',
          data: dailyOutcome.map((value) => Number(value)),
        },
      ],
    };
  }

  // for card report/month
  processReportPerMonth(transactions: any[]) {
    const today = new Date();
    const currentMonth = today.getMonth();
    const currentYear = today.getFullYear();
    const lastDayOfMonth = new Date(currentYear, currentMonth + 1, 0).getDate();

    if (currentMonth !== this.previousMonth) {
      this.incomePerMonth = Number(0);
      this.outcomePerMonth = Number(0);
    }

    let incomePerMonth = this.incomePerMonth;
    let outcomePerMonth = this.outcomePerMonth;

    for (const transaction of transactions) {
      const transactionDate = new Date(transaction.transDate);
      const transactionMonth = transactionDate.getMonth();
      const transactionYear = transactionDate.getFullYear();

      if (
        transactionYear === currentYear &&
        transactionMonth === currentMonth &&
        transactionDate.getDate() <= lastDayOfMonth
      ) {
        const amount = Number(transaction.amount);
        if (transaction.transType === 'TOP_UP') {
          incomePerMonth += amount;
        } else if (transaction.transType === 'TRANSFER') {
          outcomePerMonth += amount;
        }
      }
    }

    this.incomePerMonth = incomePerMonth;
    this.outcomePerMonth = outcomePerMonth;
    this.previousMonth = currentMonth;
  }

  ngOnInit() {
    const today = new Date();
    this.monthName = today.toLocaleString('default', { month: 'long' });

    this.getDataForChart();
    const textColor = '#333'; // hitam
    const textColorSecondary = '#666'; // abu2
    const surfaceBorder = '#ccc'; // abu2 muda

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
