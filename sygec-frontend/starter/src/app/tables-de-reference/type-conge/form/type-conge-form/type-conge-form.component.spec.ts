import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TypeCongeFormComponent } from './type-conge-form.component';

describe('TypeCongeFormComponent', () => {
  let component: TypeCongeFormComponent;
  let fixture: ComponentFixture<TypeCongeFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TypeCongeFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TypeCongeFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
