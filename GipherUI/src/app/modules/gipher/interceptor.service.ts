import { Injectable } from '@angular/core';
import { HttpInterceptor } from '@angular/common/http';
import { AuthenticationService } from '../authentication/authentication.service';
import { HttpRequest } from '@angular/common/http/src/request';
import { HttpHandler } from '@angular/common/http/src/backend';
import { Observable } from 'rxjs/internal/Observable';
import { HttpEvent } from '@angular/common/http/src/response';


@Injectable({
  providedIn: 'root'
})


export class InterceptorService implements HttpInterceptor{

  constructor(private auth: AuthenticationService) { 

  }

  intercept(request: HttpRequest<any>, next:HttpHandler): Observable<HttpEvent<any>> {
    console.log("Inside Intercept function");

    request = request.clone({
      setHeaders: {
        Authorization: `Bearer ${this.auth.getToken()}`
      }
    });

    return next.handle(request);

  }

}
