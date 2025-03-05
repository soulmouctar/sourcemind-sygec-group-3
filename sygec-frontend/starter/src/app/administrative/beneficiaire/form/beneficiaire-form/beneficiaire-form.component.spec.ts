import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BeneficiaireFormComponent } from './beneficiaire-form.component';

describe('BeneficiaireFormComponent', () => {
  let component: BeneficiaireFormComponent;
  let fixture: ComponentFixture<BeneficiaireFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BeneficiaireFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BeneficiaireFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
