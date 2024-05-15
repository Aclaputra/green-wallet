import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './payment.component.html',
  styleUrl: './payment.component.scss'
})
export class PaymentComponent {
  merchantCode: any;
  amount: any;
  info: any;

  pay(){
    console.log(this.merchantCode, this.amount, this.info)
  }
}
