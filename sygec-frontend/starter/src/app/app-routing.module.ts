import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { FullComponent } from './layouts/full/full.component';
import { BlankComponent } from './layouts/blank/blank.component';

export const routes: Routes = [
  {
    path: '',
    component: FullComponent,
    children: [
      { path: '', redirectTo: '/starter', pathMatch: 'full' },
      {
        path: 'starter',
        loadChildren: () => import('./starter/starter.module').then(m => m.StarterModule)
      },
      {
        path: 'demande',
        loadChildren: () => import('./gestion-demandes/gestion-demandes/gestion-demandes.module').then(m => m.GestionDemandesModule)
      },
      {
        path: 'reference',
      loadChildren: () => import('./tables-de-reference/tables-de-reference/tables-de-reference.module').then(m => m.TablesDeReferenceModule)
      },
      {
        path: 'authentication',
      loadChildren: () => import('./authentication/authentication/authentication.module').then(m => m.AuthenticationModule)
      },
      {
        path: 'component',
        loadChildren: () => import('./component/component.module').then(m => m.ComponentsModule)
      }
    ]
  },
  {
    path: '**',
    redirectTo: 'starter'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    useHash: true, // Changez à true pour éviter les problèmes d'actualisation
    scrollPositionRestoration: 'enabled', // Améliore l'expérience utilisateur
    relativeLinkResolution: 'legacy'
  }), NgbModule],
  exports: [RouterModule]
})
export class AppRoutingModule {}
