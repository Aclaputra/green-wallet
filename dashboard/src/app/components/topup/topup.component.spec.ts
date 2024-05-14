import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopupComponent } from './topup.component';

fdescribe('TopupComponent', () => {
  let component: TopupComponent;
  let fixture: ComponentFixture<TopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TopupComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  beforeEach(()=>{
    fixture = TestBed.createComponent(TopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should copy text to clipboard', ()=>{
    const mockText = 'test text';
    spyOn(navigator.clipboard, 'writeText').and.returnValue(Promise.resolve());

    component.copyText(mockText);
    expect(navigator.clipboard.writeText).toHaveBeenCalledWith(mockText);
  });
  
});
