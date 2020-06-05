import { HttpInterceptor, HttpRequest, HttpHandler, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TokenStorageService } from './token-storage.service';
const TOKEN_HEADER_KEY = 'Authorization';
const InterceptorSkipHeader = ''
@Injectable({
  providedIn: 'root'
})
export class AuthInterceptorService implements HttpInterceptor{

  constructor(private tokenStorage: TokenStorageService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler){
    let authReq = req;
    const token  = sessionStorage.getItem('auth-token');
    if (token != null) {
      authReq = req.clone({ headers: req.headers.set(TOKEN_HEADER_KEY, 'Bearer ' + token) });
    }

    // if(token != null) {
    //   authReq = req.clone({
    //     setHeaders: {
    //       Authorization: 'Bearer ' + token,
    //       'Content-Type': 'application/json'
    //     }
    //   });
    // }

    return next.handle(authReq);
  }

}
