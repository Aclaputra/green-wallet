import { Component, computed, signal } from '@angular/core';

import { CommonModule } from '@angular/common';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { CustomSidenavComponent } from './custom-sidenav/custom-sidenav.component';
import { MatSelectionList } from '@angular/material/list';
import { MatOptionModule } from '@angular/material/core';

@Component({
  selector: 'app-auth-nav',
  standalone: true,
  templateUrl: './auth-nav.component.html',
  styleUrl: './auth-nav.component.scss',
  imports: [
    RouterLink,
    RouterOutlet,
    CommonModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatSidenavModule,
    CustomSidenavComponent,
    MatSelectionList,
    MatOptionModule,
  ],
})
export class AuthNavComponent {
  collapsed = signal(false);
  sideNavWidth = computed(() => (this.collapsed() ? '65px' : '250px'));

  constructor(private router: Router) {}

  logout(): void {
    window.localStorage.removeItem('grn-tkn');
    window.localStorage.removeItem('tkn-exp');

    this.router.navigate(['/greenpay/home']);
  }
}
