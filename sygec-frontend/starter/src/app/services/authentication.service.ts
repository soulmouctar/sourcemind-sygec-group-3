import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { User } from '../models/user';


//import { User } from '../_helpers/user';

@Injectable({ providedIn: 'root' })
export class AuthenticationService {
  private userSubject: BehaviorSubject<User | null>;
  public user: Observable<User | null>;

  constructor(
    private router: Router,
    private http: HttpClient
  ) {
    // eslint-disable-next-line
    this.userSubject = new BehaviorSubject(JSON.parse(localStorage.getItem('user')!));
    this.user = this.userSubject.asObservable();
  }

  public get userValue() {
    return this.userSubject.value;
  }

  login(username: string, password: string) {
    return this.http.post<any>(`${environment.baseUrl}/authenticate`, { username, password },{
      headers: new HttpHeaders({
        "Content-Type": "application/json",
      }),
    }).toPromise();
  }

  logout() {
    // remove user from local storage to log user out
    //sessionStorage.removeItem('auth-token-guine');
    this.userSubject.next(null);
    sessionStorage.clear()
    this.router.navigate(['/login']);
  }
}
