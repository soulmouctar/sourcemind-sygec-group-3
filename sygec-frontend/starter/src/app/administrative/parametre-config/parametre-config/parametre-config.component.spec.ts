import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParametreConfigComponent } from './parametre-config.component';

describe('ParametreConfigComponent', () => {
  let component: ParametreConfigComponent;
  let fixture: ComponentFixture<ParametreConfigComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ParametreConfigComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ParametreConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
