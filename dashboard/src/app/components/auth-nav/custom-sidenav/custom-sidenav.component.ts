import { CommonModule } from '@angular/common';
import { Component, Input, computed, signal } from '@angular/core';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';

export type MenuItem = {
  icon: string;
  label: string;
  route: string;
  feature: boolean;
};

@Component({
  selector: 'app-custom-sidenav',
  standalone: true,
  imports: [
    CommonModule,
    MatListModule,
    MatIconModule,
    RouterLink,
    RouterLinkActive,
  ],
  templateUrl: './custom-sidenav.component.html',
  styleUrl: './custom-sidenav.component.scss',
})
export class CustomSidenavComponent {
  sideNavCollapsed = signal(false);
  @Input() set collapsed(val: boolean) {
    this.sideNavCollapsed.set(val);
  }

  menuFree = signal<MenuItem[]>([
    {
      icon: 'dashboard',
      label: 'Dashboard',
      route: 'dashboard',
      feature: false,
    },
    {
      icon: 'account_box',
      label: 'Account',
      route: 'profile',
      feature: false,
    },
    {
      icon: 'how_to_vote',
      label: 'Topup',
      route: 'topup',
      feature: false,
    },
  ]);

  menuPro = signal<MenuItem[]>([
    {
      icon: 'send',
      label: 'Transfer',
      route: 'transfer',
      feature: true,
    },
    {
      icon: 'payment',
      label: 'Payment',
      route: 'payment',
      feature: true,
    },
    {
      icon: 'library_add',
      label: 'History',
      route: 'history',
      feature: true,
    },
  ]);

  profilePicSize = computed(() => (this.sideNavCollapsed() ? '32' : '100'));

  constructor(private http: HttpClient) {
    if (typeof window !== 'undefined') {
      this.getData();
    }
  }

  resp: any;
  urlProfile: string = 'http://localhost:8080/user/profile';
  point: number = 0;
  name: string = '';
  currentSaldo: number = 0;
  features: boolean = true;
  img = '';

  getData() {
    const clientHeaders = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${window.localStorage.getItem('grn-tkn')}`,
    });
    this.http.get(this.urlProfile, { headers: clientHeaders }).subscribe(
      (data) => {
        console.log('kendrick', data);

        this.resp = data;
        this.point = this.resp.data.point;
        this.name = this.resp.data.name;
        this.img = this.resp.data.profileImageUrl;
        console.log('Saldo saat ini:', this.resp.data.balance);
        console.log('Current Saldo:', this.currentSaldo);
        this.currentSaldo = this.resp.data.balance;
        console.log('After edit:', this.currentSaldo);
      },
      (error) => {
        console.error('Error fetch profile:', error);
        if (error.status == 403) {
          window.localStorage.clear();
        }
      }
    );
  }
}
