import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SuivreMaDemandeFormComponent } from './suivre-ma-demande-form.component';

describe('SuivreMaDemandeFormComponent', () => {
  let component: SuivreMaDemandeFormComponent;
  let fixture: ComponentFixture<SuivreMaDemandeFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SuivreMaDemandeFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SuivreMaDemandeFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
