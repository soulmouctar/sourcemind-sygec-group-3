import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule, NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';

import { LoyoutComponent } from '../loyout/loyout/loyout.component';
import LoginComponent from '../login/login/login.component';



const AuthenticationRoutes: Routes = [
  {
    path: 'authentication/login',
    data: {
      title: 'Faire une Demande',
      urls: [
        { title: 'AUTHENTICATION', url: '/login' },
        { title: 'Faire une Demande' }
      ]
    },
    component: LoginComponent
  },
  {
    path: 'authentication/loyout',
    data: {
      title: 'Suivre ma Demande',
      urls: [
        { title: 'AUTHENTICATION', url: '/loyout' },
        { title: 'Suivre ma Demande' }
      ]
    },
    component: LoyoutComponent
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    NgbModule,
    ReactiveFormsModule,
    NgbPaginationModule,
    RouterModule.forChild(AuthenticationRoutes)
  ],
  declarations: [
    LoginComponent,
    LoyoutComponent
  ]
})

export class AuthenticationModule { }
