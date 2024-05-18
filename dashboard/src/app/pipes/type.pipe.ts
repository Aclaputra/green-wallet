import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'type',
  standalone: true,
})
export class TypePipe implements PipeTransform {
  transform(value: string, ...args: string[]): string {
    if (value.includes('TRANSFER')) {
      return value
    } else if (value.includes('TOP_UP')) {
      return value;
    } else if( value.includes('PAYMENT')) {
      return value
    }
    return 'unknown';
  }
}
