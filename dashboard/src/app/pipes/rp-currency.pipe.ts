import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'rpCurrency',
  standalone: true,
})
export class RpCurrencyPipe implements PipeTransform {
  transform(value: number): string {
    if (value === null || value === undefined) return '';

    const formattedValue = value
      .toString()
      .replace(/\B(?=(\d{3})+(?!\d))/g, '.');
    return `Rp ${formattedValue}`;
  }
}
