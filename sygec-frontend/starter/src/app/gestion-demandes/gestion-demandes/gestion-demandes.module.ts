import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { DemandeListComponent } from '../demandes/list/demande-list/demande-list.component';
import { DemandeFormComponent } from '../demandes/form/demande-form/demande-form.component';
import { NgbModule, NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { FullComponent } from '../../layouts/full/full.component';
import { SuivreMaDemandeListComponent } from '../suivre-ma-demande/list/suivre-ma-demande-list/suivre-ma-demande-list.component';

import { StarterComponent } from '../../starter/starter.component';
import { ListeDesDemandesListComponent } from '../listes-des-demandes/list/liste-des-demandes-list/liste-des-demandes-list.component';


const GestionDemandeRoutes: Routes = [
  
  {
    path: 'demande',
    data: {
      title: 'Faire une Demande',
      urls: [
        { title: 'GESTION DEMANDES', url: '/demande' },
        { title: 'Faire une Demande' }
      ]
    },
    component: DemandeListComponent
  },
  {
    path: 'suivre-ma-demande',
    data: {
      title: 'Suivre ma Demande',
      urls: [
        { title: 'GESTION DEMANDES', url: '/suivre-ma-demande' },
        { title: 'Suivre ma Demande' }
      ]
    },
    component: SuivreMaDemandeListComponent
  },
  {
    path: 'listes-des-demandes',
    data: {
      title: 'Listes des Demandes',
      urls: [
        { title: 'GESTION DEMANDES', url: '/listes-des-demandes' },
        { title: 'Listes des Demandes' }
      ]
    },
    component: ListeDesDemandesListComponent
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    NgbModule,
    ReactiveFormsModule,
    NgbPaginationModule,
    RouterModule.forChild(GestionDemandeRoutes)
  ],
  declarations: [
    DemandeListComponent,
    DemandeFormComponent
  ]
})
export class GestionDemandesModule { }
