import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListeDesDemandesListComponent } from './liste-des-demandes-list.component';

describe('ListeDesDemandesListComponent', () => {
  let component: ListeDesDemandesListComponent;
  let fixture: ComponentFixture<ListeDesDemandesListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListeDesDemandesListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListeDesDemandesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
