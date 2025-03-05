import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../app/services/authentication.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { environment } from '../environments/environment';





@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(
    private authenticationService: AuthenticationService,
    private tokenStorageService: TokenStorageService
  ) {}

  intercept(request: HttpRequest<string>, next: HttpHandler): Observable<HttpEvent<string>> {
    // add auth header with jwt if user is logged in and request is to the api url
    const user = this.authenticationService['userValue'];
    const isLoggedIn = user?.token;
    const isApiUrl = request.url.startsWith(environment.baseUrl);
    if (isLoggedIn && isApiUrl) {
      request = request.clone({
        setHeaders: {
          Authorization: 
          //  `Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImV4cCI6MTczMzg1NDM1NSwiaWF0IjoxNzMzODM2MzU1LCJqdGkiOiJjNjIyZDcxNi1mMzc3LTQ2YzUtOGYxMy1lNmUzY2Y2YjkwNzMifQ.B5hMVBMj1yHvxDFSi1x6DbtAFoCDqQrxsnxZZBduhivmOR4X0TladhVkEm9WG4CyJh3PEjO52EJR8yyxE3-gbQ`
           this.tokenStorageService.getToken()
          
        }
      });
    }

    return next.handle(request);
  }
}
