import { Routes } from '@angular/router';
import { ProfileComponent } from './components/profile/profile.component';

export const routes: Routes = [
    {
        path: 'profile',
        title: 'Profile Page',
        component: ProfileComponent
    }
];
