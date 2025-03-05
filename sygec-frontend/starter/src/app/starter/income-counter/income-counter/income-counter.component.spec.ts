import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IncomeCounterComponent } from './income-counter.component';

describe('IncomeCounterComponent', () => {
  let component: IncomeCounterComponent;
  let fixture: ComponentFixture<IncomeCounterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IncomeCounterComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IncomeCounterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
