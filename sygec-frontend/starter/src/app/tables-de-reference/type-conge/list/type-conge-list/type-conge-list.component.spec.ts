import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TypeCongeListComponent } from './type-conge-list.component';

describe('TypeCongeListComponent', () => {
  let component: TypeCongeListComponent;
  let fixture: ComponentFixture<TypeCongeListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TypeCongeListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TypeCongeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
