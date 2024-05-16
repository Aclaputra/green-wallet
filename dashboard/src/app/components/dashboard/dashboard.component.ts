import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ChartModule } from 'primeng/chart';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

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
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent {
  data: any;
  options: any;

  ngOnInit() {
    const textColor = '#333'; // hitam
    const textColorSecondary = '#666'; // abu2
    const surfaceBorder = '#ccc'; // abu2 muda
    const greenChartColor = '#198b0e'; // hijau
    const redChartColor = '#FF0000'; // merah

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
          backgroundColor: greenChartColor,
          borderColor: greenChartColor,
          data: [65000, 59000, 80000, 81000, 56000, 55000, 40000],
        },
        {
          label: 'Outcome',
          backgroundColor: redChartColor,
          borderColor: redChartColor,
          data: [28000, 48000, 40000, 19000, 86000, 27000, 90000],
        },
      ],
    };

    this.options = {
      maintainAspectRatio: false,
      aspectRatio: 0.8,
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
