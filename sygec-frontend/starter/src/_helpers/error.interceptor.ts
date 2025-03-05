import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthenticationService } from '../app/services/authentication.service';





@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(private authenticationService: AuthenticationService) {}

  intercept(request: HttpRequest<string>, next: HttpHandler): Observable<HttpEvent<string>> {
    return next.handle(request).pipe(
      catchError((err) => {
        if ([401, 403].includes(err.status)) {
          // auto logout if 401 Unauthorized or 403 Forbidden response returned from api
          // auto logout if 401 Unauthorized or 403 Forbidden response returned from api
          this.authenticationService['logout']();
        }

        const error = err.error.message || err.statusText;
        return throwError(error);
      })
    );
  }
}
