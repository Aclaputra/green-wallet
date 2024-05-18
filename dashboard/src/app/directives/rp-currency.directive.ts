import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
  selector: '[appRpCurrency]',
  standalone: true,
})
export class RpCurrencyDirective {
  private el: HTMLInputElement;

  constructor(private elementRef: ElementRef) {
    this.el = this.elementRef.nativeElement;
  }

  @HostListener('input', ['$event.target.value'])
  onInput(value: string): void {
    this.el.value = this.formatCurrency(value);
  }

  private formatCurrency(value: string): string {
    if (!value) {
      return '';
    }
    const cleanedValue = value.replace(/[^\d.-]/g, '');
    const numberValue = parseFloat(cleanedValue);
    if (isNaN(numberValue)) {
      return '';
    }
    return (
      'Rp ' +
      numberValue.toLocaleString('id-ID', {
        minimumFractionDigits: 0,
        maximumFractionDigits: 0,
      })
    );
  }
}
