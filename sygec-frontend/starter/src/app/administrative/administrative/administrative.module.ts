import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdministrativeComponent } from './administrative.component';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule, NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { AdminFormComponent } from '../admin/form/admin-form/admin-form.component';

import { BeneficiaireFormComponent } from '../beneficiaire/form/beneficiaire-form/beneficiaire-form.component';
import { adminListComponent } from '../admin/list/admin-list/admin-list.component';
import { beneficiaireListComponent } from '../beneficiaire/list/beneficiaire-list/beneficiaire-list.component';
import { ParametreConfigComponent } from '../parametre-config/parametre-config/parametre-config.component';



const AdministrationRoutes: Routes = [
  
  {
    path: 'authentication',
    data: {
      title: 'ADMINISTRATION',
      urls: [
        { title: 'ADMINISTRATION', url: '/authentication' },
        { title: 'ADMINISTRATION' }
      ]
    },
    component: AdministrativeComponent
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    NgbModule,
    ReactiveFormsModule,
    NgbPaginationModule,
    RouterModule.forChild(AdministrationRoutes)
  ],
  declarations: [
    AdminFormComponent,
    adminListComponent,
    BeneficiaireFormComponent,
    beneficiaireListComponent,
    AdministrativeComponent,
    ParametreConfigComponent
  ]
})
export class AdministrativeModule { }
