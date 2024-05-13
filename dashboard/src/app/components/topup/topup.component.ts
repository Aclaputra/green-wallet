import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-topup',
  standalone: true,
  imports: [],
  templateUrl: './topup.component.html',
  styleUrl: './topup.component.scss'
})
export class TopupComponent {
  merchants= {
    data: [
      {
        name: "Indomaret",
        nomor: "2348 9 9843 8423 8492",
        image: "../../../assets/indomaret.png"
      },
      {
        name: "Alfamart",
        nomor: "7823 6 2618 3764 9837",
        image: "../../../assets/alfamart.png"
      },
      {
        name: "DanDan",
        nomor: "9824 7 8918 9813 7634",
        image: "../../../assets/dandan.png"
      },
      {
        name: "Alfamidi",
        nomor: "1234 6 8723 9871 8471",
        image: "../../../assets/alfamidi.png"
      },
      {
        name: "Lawson",
        nomor: "8713 7 7625 1273 9871",
        image: "../../../assets/lawson.png"
      }
    ]
  }

  currentSaldo: number = 0;

  @Input() merchant: any;

  copyText(text:string){
    navigator.clipboard.writeText(text).then(()=>{
      console.log("Copied");
    }, (err)=>{
      console.error("Failed to Copy")
    })
  }
}
