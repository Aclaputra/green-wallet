import { CanActivateFn } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  if(typeof window !== "undefined"){
    if(window.localStorage.getItem("grn-tkn")!=null){
      return true;
    }else{
      return false;
    }
  }
  return false;
};
