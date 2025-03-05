import * as $ from 'jquery';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CommonModule, LocationStrategy, HashLocationStrategy } from '@angular/common';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';

// ng-bootstrap imports
import { 
  NgbModule, 
  NgbAccordionModule, 
  NgbPaginationModule, 
  NgbNavModule,  // Ajouté pour la navigation
  NgbCarouselModule  // Ajouté pour le carousel
} from '@ng-bootstrap/ng-bootstrap';

// Perfect Scrollbar
import { PerfectScrollbarModule, PERFECT_SCROLLBAR_CONFIG, PerfectScrollbarConfigInterface } from 'ngx-perfect-scrollbar';

// Multi-select dropdown
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';

// Components
import { AppComponent } from './app.component';
import { FullComponent } from './layouts/full/full.component';
import { BlankComponent } from './layouts/blank/blank.component';
import { NavigationComponent } from './shared/header-navigation/navigation.component';
import { SidebarComponent } from './shared/sidebar/sidebar.component';
import { BreadcrumbComponent } from './shared/breadcrumb/breadcrumb.component';
import { SpinnerComponent } from './shared/spinner.component';

// Modules
import { AppRoutingModule } from './app-routing.module';
import { GestionDemandesModule } from './gestion-demandes/gestion-demandes/gestion-demandes.module';
import { TablesDeReferenceModule } from './tables-de-reference/tables-de-reference/tables-de-reference.module';
import { ListeDesDemandesFormComponent } from './gestion-demandes/listes-des-demandes/form/liste-des-demandes-form/liste-des-demandes-form.component';
import { ListeDesDemandesListComponent } from './gestion-demandes/listes-des-demandes/list/liste-des-demandes-list/liste-des-demandes-list.component';
import { SuivreMaDemandeFormComponent } from './gestion-demandes/suivre-ma-demande/form/suivre-ma-demande-form/suivre-ma-demande-form.component';
import { SuivreMaDemandeListComponent } from './gestion-demandes/suivre-ma-demande/list/suivre-ma-demande-list/suivre-ma-demande-list.component';
import { AuthenticationModule } from './authentication/authentication/authentication.module';
import { LoyoutComponent } from './authentication/loyout/loyout/loyout.component';
import { AdminFormComponent } from './administrative/admin/form/admin-form/admin-form.component';

import { BeneficiaireFormComponent } from './administrative/beneficiaire/form/beneficiaire-form/beneficiaire-form.component';

import { AdministrativeComponent } from './administrative/administrative/administrative.component';

import { JwtInterceptor } from '../_helpers/jw.interceptor';
import { ErrorInterceptor } from '../_helpers/error.interceptor';
import { AdministrativeModule } from './administrative/administrative/administrative.module';
import { ParametreConfigComponent } from './administrative/parametre-config/parametre-config/parametre-config.component';
import { authInterceptorProviders } from '../_helpers/auth.interceptor';

const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  suppressScrollX: true,
  wheelSpeed: 2,
  wheelPropagation: true
};

@NgModule({
  declarations: [
    AppComponent,
    SpinnerComponent,
    FullComponent,
    BlankComponent,
    NavigationComponent,
    BreadcrumbComponent,
    SidebarComponent,
    ListeDesDemandesFormComponent,
    ListeDesDemandesListComponent,
    SuivreMaDemandeFormComponent,
    SuivreMaDemandeListComponent
   
  
  ],
  imports: [
    // Angular Modules
    CommonModule,
    BrowserModule,
    BrowserAnimationsModule,
    AuthenticationModule,
    AdministrativeModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    
    // ng-bootstrap Modules
    NgbModule,
      NgbAccordionModule,
      NgbPaginationModule,
      NgbNavModule,
      NgbCarouselModule,
   
    
    // Feature Modules
    GestionDemandesModule,
    TablesDeReferenceModule,
    
    // Other Modules
    NgMultiSelectDropDownModule.forRoot(),
    PerfectScrollbarModule,
    AppRoutingModule
  ],
  providers: [
    
      authInterceptorProviders,
      { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
      { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    

  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  bootstrap: [AppComponent]
})
export class AppModule {}