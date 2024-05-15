import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { ProfileComponent } from './components/profile/profile.component';
import { TransferComponent } from './components/transfer/transfer.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';
import { LandingComponent } from './components/landing/landing.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { TopupComponent } from './components/topup/topup.component';
import { RegisterComponent } from './components/register/register.component';
import { PaymentComponent } from './components/payment/payment.component';

export const routes: Routes = [
  {
    path: '',
    title: 'Default',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path: 'home',
    title: 'Home',
    component: LandingComponent
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
  },
  {
    path: 'login',
    title: 'Login',
    component: LoginComponent
  },
  {
    path: 'register',
    title: 'Register',
    component: RegisterComponent
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
    path: 'topup',
    component: TopupComponent
  },
  {
    path: 'transfer',
    component: TransferComponent,
  },
  {
    path: 'payment',
    component: PaymentComponent
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];
