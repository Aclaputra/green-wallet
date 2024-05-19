import { Component, ElementRef, ViewChild } from '@angular/core';
import { PlantCarouselComponent } from '../plant-carousel/plant-carousel.component';

@Component({
  selector: 'app-landing',
  standalone: true,
  imports: [PlantCarouselComponent],
  templateUrl: './landing.component.html',
  styleUrl: './landing.component.scss',
})
export class LandingComponent {
  @ViewChild('aboutContent') aboutContentElement!: ElementRef;

  contentAbout: string =
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.';

  constructor() {}

  ngAfterViewInit(): void {
    this.typeWriterEffect();
  }

  typeWriterEffect() {
    const typingText = this.aboutContentElement.nativeElement;
    const text = this.contentAbout.trim();
    let charIndex = 0;

    const type = () => {
      if (charIndex < text.length) {
        typingText.textContent += text.charAt(charIndex);
        charIndex++;
        setTimeout(type, 30);
      }
    };

    type();
  }
}
