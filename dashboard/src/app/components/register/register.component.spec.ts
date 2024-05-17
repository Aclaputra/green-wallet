import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RegisterComponent } from './register.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ToastrModule } from 'ngx-toastr';
import { RouterTestingModule } from '@angular/router/testing';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RegisterComponent,HttpClientTestingModule, ToastrModule.forRoot(), RouterTestingModule.withRoutes([])],
      providers: [HttpClientTestingModule],
    }).compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should create register form', () => {
    expect(component.registerForm).toBeTruthy();
  });

  it('should create email form control', () => {
    expect(component.registerForm.get('email')).toBeTruthy();
  });

  it('should create password form control', () => {
    expect(component.registerForm.get('password')).toBeTruthy();
  });

  it('should create name form control', () => {
    expect(component.registerForm.get('name')).toBeTruthy();
  });

  it('should create phoneNumber form control', () => {
    expect(component.registerForm.get('phoneNumber')).toBeTruthy();
  });

  it('should create birthDate form control', () => {
    expect(component.registerForm.get('birthDate')).toBeTruthy();
  });

  it('should create username form control', () => {
    expect(component.registerForm.get('username')).toBeTruthy();
  });
});
