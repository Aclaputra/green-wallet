import { Component } from '@angular/core';
import { PlantDataService } from '../../services/plant-data.service';
import { CommonModule } from '@angular/common';

export interface DataPlant {
  common_name: string;
  scientific_name: string;
  bibliography: string;
  image_url: string;
}

@Component({
  selector: 'app-plant-carousel',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './plant-carousel.component.html',
  styleUrl: './plant-carousel.component.scss',
})
export class PlantCarouselComponent {
  // get date
  date: Date = new Date();
  datePage: number = this.date.getDate();

  // get data
  dataPlants: DataPlant[] = [];

  constructor(private plantDataService: PlantDataService) {}

  getDataCarousel() {
    this.plantDataService
      .getPlantData(
        `https://trefle.io/api/v1/plants?page=${this.datePage}&token=tZrxQ801TUyZXs6rzOWCA9t_ux0qgFWM3w565-7vZ9g`
      )
      .subscribe({
        next: (resp: any) => {
          this.dataPlants = resp.data;
          console.info('arr data plants', this.dataPlants);
          // this.dataCarousel(resp.data);
        },
        error: (err) => {
          console.error(err);
        },
      });
  }

  // dataCarousel(dataPlants: any[]) {
  //   const dataIndex = dataPlants.map((data) => {
  //     console.info(data.id);
  //     return data.id;
  //   });
  // }

  ngOnInit(): void {
    //Called after the constructor, initializing input properties, and the first call to ngOnChanges.
    //Add 'implements OnInit' to the class.
    this.getDataCarousel();
  }
}
