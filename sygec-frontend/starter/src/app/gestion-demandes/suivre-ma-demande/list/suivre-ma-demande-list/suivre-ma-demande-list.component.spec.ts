import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SuivreMaDemandeListComponent } from './suivre-ma-demande-list.component';

describe('SuivreMaDemandeListComponent', () => {
  let component: SuivreMaDemandeListComponent;
  let fixture: ComponentFixture<SuivreMaDemandeListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SuivreMaDemandeListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SuivreMaDemandeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
