import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'type',
  standalone: true
})
export class TypePipe implements PipeTransform {

  transform(value: string, ...args: string[]): string {
    if(value.includes("transfer" || "Transfer")) {  
      return "blue"
    }else if(value.includes("topup" || "Topup")) {
      return "green"
    }
    return "unknown"
  }

}
