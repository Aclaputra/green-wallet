import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthNavNComponent } from './auth-nav-n.component';

describe('AuthNavNComponent', () => {
  let component: AuthNavNComponent;
  let fixture: ComponentFixture<AuthNavNComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AuthNavNComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AuthNavNComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
