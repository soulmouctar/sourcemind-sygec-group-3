import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TablesDeReferenceComponent } from './tables-de-reference.component';

describe('TablesDeReferenceComponent', () => {
  let component: TablesDeReferenceComponent;
  let fixture: ComponentFixture<TablesDeReferenceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TablesDeReferenceComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TablesDeReferenceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
