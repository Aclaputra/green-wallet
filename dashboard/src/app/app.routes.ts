import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { ProfileComponent } from './components/profile/profile.component';
import { TransferComponent } from './components/transfer/transfer.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';
import { LandingComponent } from './components/landing/landing.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { TopupComponent } from './components/topup/topup.component';
import { AuthNavComponent } from './components/auth-nav/auth-nav.component';
import { RegisterComponent } from './components/register/register.component';
import { PaymentComponent } from './components/payment/payment.component';
import { AuthNavNComponent } from './components/auth-nav-n/auth-nav-n.component';

export const routes: Routes = [
  // {
  //   path: '',
  //   title: 'Default',
  //   redirectTo: 'home',
  //   pathMatch: 'full',
  // },
  {
    path: 'auth',
    component: AuthNavComponent,
    children: [
      {
        path: 'profile',
        title: 'Profile Page',
        component: ProfileComponent,
      },
      {
        path: 'dashboard',
        title: 'Dashboard Page',
        component: DashboardComponent,
      },
      {
        path: 'transfer',
        title: 'Transfer Page',
        component: TransferComponent,
      },
      {
        path: 'topup',
        title: 'Topup',
        component: TopupComponent,
      },
      {
        path: 'payment',
        title: 'Payment',
        component: PaymentComponent
      }
    ],
  },
  {
    path: 'nauth',
    component: AuthNavNComponent,
    children: [
      {
        path: 'home',
        title: 'Home',
        component: LandingComponent,
      },
      {
        path: 'login',
        title: 'Login Page',
        component: LoginComponent,
      },
      {
        path: 'register',
        title: 'Register Page',
        component: RegisterComponent,
      },
      {
        path: 'forgot-password',
        component: ForgotPasswordComponent,
      },
    ]
  },
  {
    path: '**',
    component: NotFoundComponent,
  },
];
