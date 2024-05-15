import { Component } from '@angular/core';
import { TypePipe } from '../../../pipes/type.pipe';

@Component({
  selector: 'app-details',
  standalone: true,
  imports: [TypePipe],
  templateUrl: './details.component.html',
  styleUrl: './details.component.scss'
})
export class DetailsComponent {
onConfirm() {
  throw new Error('Method not implemented.');
}

}
