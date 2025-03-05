import { TestBed } from '@angular/core/testing';

import { JwInterceptor } from './jw.interceptor';

describe('JwInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      JwInterceptor
      ]
  }));

  it('should be created', () => {
    const interceptor: JwInterceptor = TestBed.inject(JwInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
