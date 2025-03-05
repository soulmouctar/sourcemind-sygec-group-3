import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListeDesDemandesFormComponent } from './liste-des-demandes-form.component';

describe('ListeDesDemandesFormComponent', () => {
  let component: ListeDesDemandesFormComponent;
  let fixture: ComponentFixture<ListeDesDemandesFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListeDesDemandesFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListeDesDemandesFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
