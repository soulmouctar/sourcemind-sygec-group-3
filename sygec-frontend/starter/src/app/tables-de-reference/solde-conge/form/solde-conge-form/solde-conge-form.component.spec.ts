import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SoldeCongeFormComponent } from './solde-conge-form.component';

describe('SoldeCongeFormComponent', () => {
  let component: SoldeCongeFormComponent;
  let fixture: ComponentFixture<SoldeCongeFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SoldeCongeFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SoldeCongeFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
