import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class TokenService {
  tokenExpiredHour: number = 1;

  constructor() {}

  countDownDeleteTokenLocalStorage() {
    let currentTime = new Date().getTime();
    // console.log("Waktu saat login:", Number(window.localStorage.getItem("tkn-exp"))+(this.tokenExpiredHour*60*60*1000));
    // console.log("Waktu saat ini:  ", currentTime);
    // console.log((Number(window.localStorage.getItem("tkn-exp"))+(this.tokenExpiredHour*60*60*1000))<currentTime);
    if (
      Number(window.localStorage.getItem('tkn-exp')) +
        this.tokenExpiredHour * 60 * 60 * 1000 <
      currentTime
    ) {
      localStorage.clear();
    }
  }

  isTokenInLocalStorage() {
    if (window.localStorage.getItem('grn-tkn') == null) {
      return false;
    } else {
      return true;
    }
  }

  // to authorize use isTokenInLocalStorage
  // to delete token use countDownDeleteTokenLocalStorage
}
