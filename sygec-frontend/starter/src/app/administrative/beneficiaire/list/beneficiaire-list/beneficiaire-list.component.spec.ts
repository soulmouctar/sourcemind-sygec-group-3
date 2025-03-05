import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BeneficiaireListComponent } from './beneficiaire-list.component';

describe('BeneficiaireListComponent', () => {
  let component: BeneficiaireListComponent;
  let fixture: ComponentFixture<BeneficiaireListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BeneficiaireListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BeneficiaireListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
