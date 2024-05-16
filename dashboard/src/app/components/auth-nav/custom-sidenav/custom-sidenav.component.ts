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

  menuItems = signal<MenuItem[]>([
    {
      icon: 'dashboard',
      label: 'Dashboard',
      route: 'dashboard',
    },
    {
      icon: 'account_box',
      label: 'Account',
      route: 'profile',
    },
    {
      icon: 'history',
      label: 'History',
      route: 'history',
    },
    {
      icon: 'library_add',
      label: 'Transfer',
      route: 'transfer',
    },
    {
      icon: 'account_balance_wallet',
      label: 'Topup',
      route: 'topup',
    },
    {
      icon: 'money',
      label: 'Payment',
      route: 'payment',
    },
  ]);

  profilePicSize = computed(() => (this.sideNavCollapsed() ? '32' : '100'));

  constructor(private http: HttpClient) {}

  resp: any;
  urlProfile: string = 'http://localhost:8080/user/profile';
  point: number = 0;
  name: string = '';

  ngOnInit() {
    if (typeof window !== 'undefined') {
      this.getData();
    }
  }

  getData() {
    const clientHeaders = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${window.localStorage.getItem('grn-tkn')}`,
    });
    this.http.get(this.urlProfile, { headers: clientHeaders }).subscribe(
      (data) => {
        this.resp = data;
        this.point = this.resp.data.point;
        this.name = this.resp.data.name;
      },
      (error) => {
        console.error('Error fetch profile:', error);
      }
    );
  }
}
