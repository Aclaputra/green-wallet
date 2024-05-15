import { TestBed } from '@angular/core/testing';

import { TokenService } from './token.service';

fdescribe('TokenService', () => {
  let service: TokenService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TokenService);
  });

  afterEach(()=>{
    localStorage.clear();
  })

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return false if token is not in localStorage', () => {
    expect(service.isTokenInLocalStorage()).toBeFalse();
  });

  it('should return true if token is in localStorage', () => {
    localStorage.setItem('grn-tkn', 'test-token');
    expect(service.isTokenInLocalStorage()).toBeTrue();
  });

  it('should clear localStorage if token is expired', () => {
    const pastTime = new Date().getTime() - (2 * 60 * 60 * 1000);
    localStorage.setItem('tkn-exp', pastTime.toString());
    service.countDownDeleteTokenLocalStorage();
    expect(localStorage.getItem('tkn-exp')).toBeNull();
  });

  it('should not clear localStorage if token is not expired', () => {
    const futureTime = new Date().getTime() + (2 * 60 * 60 * 1000);
    localStorage.setItem('tkn-exp', futureTime.toString());
    localStorage.setItem('grn-tkn', 'test-token');
    service.countDownDeleteTokenLocalStorage();
    expect(localStorage.getItem('tkn-exp')).not.toBeNull();
    expect(localStorage.getItem('grn-tkn')).toBe('test-token');
  });
});
