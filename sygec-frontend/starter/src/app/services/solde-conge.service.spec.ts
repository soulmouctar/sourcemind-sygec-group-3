import { TestBed } from '@angular/core/testing';

import { SoldeCongeService } from './solde-conge.service';

describe('SoldeCongeService', () => {
  let service: SoldeCongeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SoldeCongeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
