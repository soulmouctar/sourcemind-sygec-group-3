import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { StarterComponent } from './starter.component';
import { ChartistModule } from 'ng-chartist';
import { IncomeCounterComponent } from './income-counter/income-counter/income-counter.component';
import { NgbCarouselModule } from '@ng-bootstrap/ng-bootstrap';

const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Starter Page',
      urls: [
        { title: 'Dashboard', url: '/dashboard' },
        { title: 'Starter Page' }
      ]
    },
    component: StarterComponent
  }
];

@NgModule({
  imports: [FormsModule, CommonModule,
    ChartistModule,
    ChartistModule,
    NgbCarouselModule,
    ChartistModule,
     RouterModule.forChild(routes)],
  declarations: [StarterComponent, IncomeCounterComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StarterModule {}
