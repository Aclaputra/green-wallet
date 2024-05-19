import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlantCarouselComponent } from './plant-carousel.component';

describe('PlantCarouselComponent', () => {
  let component: PlantCarouselComponent;
  let fixture: ComponentFixture<PlantCarouselComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlantCarouselComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PlantCarouselComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
