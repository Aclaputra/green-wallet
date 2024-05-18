import { Component } from '@angular/core';
import { PlantDataService } from '../../services/plant-data.service';

@Component({
  selector: 'app-plant-carousel',
  standalone: true,
  imports: [],
  templateUrl: './plant-carousel.component.html',
  styleUrl: './plant-carousel.component.scss',
})
export class PlantCarouselComponent {
  constructor(private plantDataService: PlantDataService) {}

  getDataCarousel() {
    this.plantDataService
      .getPlantData(
        'https://trefle.io/api/v1/plants?token=tZrxQ801TUyZXs6rzOWCA9t_ux0qgFWM3w565-7vZ9g'
      )
      .subscribe({
        next: (resp: any) => {
          console.log(resp);
        },
        error: (err) => {
          console.error(err);
        },
      });
  }

  ngOnInit(): void {
    //Called after the constructor, initializing input properties, and the first call to ngOnChanges.
    //Add 'implements OnInit' to the class.
    this.getDataCarousel();
  }
}
