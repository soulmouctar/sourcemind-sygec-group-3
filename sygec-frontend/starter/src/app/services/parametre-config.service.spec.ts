import { TestBed } from '@angular/core/testing';

import { ParametreConfigService } from './parametre-config.service';

describe('ParametreConfigService', () => {
  let service: ParametreConfigService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ParametreConfigService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
