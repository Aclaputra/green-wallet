import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthNavNComponent } from './auth-nav-n.component';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

describe('AuthNavNComponent', () => {
  let component: AuthNavNComponent;
  let fixture: ComponentFixture<AuthNavNComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AuthNavNComponent, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { 
            snapshot: { 
              params: { id: 1}
            } 
          }
        }
      ]
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
