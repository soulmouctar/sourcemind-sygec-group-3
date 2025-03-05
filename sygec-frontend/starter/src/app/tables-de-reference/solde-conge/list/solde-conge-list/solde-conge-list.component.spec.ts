import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SoldeCongeListComponent } from './solde-conge-list.component';

describe('SoldeCongeListComponent', () => {
  let component: SoldeCongeListComponent;
  let fixture: ComponentFixture<SoldeCongeListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SoldeCongeListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SoldeCongeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
