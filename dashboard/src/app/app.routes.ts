import { Routes } from '@angular/router';
import { ProfileComponent } from './components/profile/profile.component';
import { TransferComponent } from './components/transfer/transfer.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';

export const routes: Routes = [
  {
    path: 'dashboard',
    component: DashboardComponent,
  },
  {
    path: 'profile',
    title: 'Profile Page',
    component: ProfileComponent
  },
  {
    path: 'forgot-password',
    component: ForgotPasswordComponent,
  },
  {
    path: 'transfer',
    component: TransferComponent,
  },
];
