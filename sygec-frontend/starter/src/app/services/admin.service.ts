import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Admin } from '../models/admin';



@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private readonly adminNameUrl: string =`${environment.baseUrl}/admin`;
  
  constructor(private http: HttpClient) { }

  comboRoles(): Observable<string[]> {
    return this.http.get<string[]>(`${environment.baseUrl}/role`, {
      headers: new HttpHeaders({
        "Content-Type": "application/json",
      }),
    });
  }

  newAdmin(newAdmin: any): Observable<Admin> {
    return this.http.post<Admin>(this.adminNameUrl, newAdmin, {
      headers: new HttpHeaders({
        "Content-Type": "application/json",
      }),
    });
  }

  
  updateAdmin(admin: any) {
    return this.http.put<Admin>(`${this.adminNameUrl}`, admin, {
      headers: new HttpHeaders({
        "Content-Type": "application/json",
      }),
    });
  }

  changeEmailAndPassword(username: any,password:any) {
    return this.http.post<any>(`${this.adminNameUrl}/changePassword`, {username,password}, {
      headers: new HttpHeaders({
        "Content-Type": "application/json",
      }),
    });
  }

  deleteAdmin(admin: Admin) {
    return this.http
      .delete<Admin>(`${this.adminNameUrl}/${admin.uuid}`, {
        headers: new HttpHeaders({
          "Content-Type": "application/json",
        }),
      })
      .toPromise();
  }
  
  getAdmin(uuid: String): Observable<Admin> {
    return this.http.get<Admin>(this.adminNameUrl, {
      headers: new HttpHeaders({
        "Content-Type": "application/json",
      }),
    });
  }

  getAdminByEmail(email: String) {
    return this.http.get<Admin>(`${this.adminNameUrl}/byEmail/${email}`, {
      headers: new HttpHeaders({
        "Content-Type": "application/json",
      }) 
    })
    .toPromise();
  }

  // getAdmins(page:Page,key:string): Observable<any> {
  //   return this.http.get<any>(`${this.adminNameUrl}?key=${key}&page=${page.pageNumber}&size=${page.size}`, 
  //   {
  //     headers: new HttpHeaders({
  //       "Content-Type": "application/json",
  //     }),
  //   });
  // }

  // public deleteAdmin (uuidAdmin :String) : Observable<any> {
  //   return  this.http.delete<void>(`http://localhost:8081/Admin/delete/${uuidAdmin}`)
  // }

  public getAllAdmin () : Observable<Admin[]> {
    return  this.http.get<Admin[]>(`http://localhost:8081/admin/all`)
  }
}
