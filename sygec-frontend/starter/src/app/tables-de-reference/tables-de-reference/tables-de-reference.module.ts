import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { NgbModule, NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TablesDeReferenceComponent } from './tables-de-reference.component';
import { PosteFormComponent } from '../poste/form/poste-form/poste-form.component';
import { PosteListComponent } from '../poste/list/poste-list/poste-list.component';
import { serviceFormComponent } from '../service/form/service-form/service-form.component';
import { ServiceListComponent } from '../service/list/service-list/service-list.component';
import { SoldeCongeFormComponent } from '../solde-conge/form/solde-conge-form/solde-conge-form.component';
import { SoldeCongeListComponent } from '../solde-conge/list/solde-conge-list/solde-conge-list.component';
import { TypeCongeFormComponent } from '../type-conge/form/type-conge-form/type-conge-form.component';
import { TypeCongeListComponent } from '../type-conge/list/type-conge-list/type-conge-list.component';
import { StarterComponent } from '../../starter/starter.component';


const TableDeReferenceRoutes: Routes = [

  {
    path: 'reference',
    data: {
      title: 'Tables des Réferences',
      urls: [
        { title: 'REFERENCES', url: '/reference' },
        { title: 'Tables des Réferences' }
      ]
    },
    component: TablesDeReferenceComponent
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    NgbModule,
    ReactiveFormsModule,
    NgbPaginationModule,
    RouterModule.forChild(TableDeReferenceRoutes)
  ],
  declarations: [
    PosteFormComponent,
    PosteListComponent,
    serviceFormComponent,
    ServiceListComponent,
    SoldeCongeFormComponent,
    SoldeCongeListComponent,
    TypeCongeFormComponent,
    TypeCongeListComponent,
    TablesDeReferenceComponent
  ]
})
export class TablesDeReferenceModule { }
