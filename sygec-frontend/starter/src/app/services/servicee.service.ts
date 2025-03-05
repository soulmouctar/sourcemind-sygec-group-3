import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Servicee } from '../models/servicee';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ServiceeService {

  constructor(private http : HttpClient) {
  }
  
  public newServicee (ServiceeToAdd :Servicee) : Observable<Servicee> {
    return  this.http.post<Servicee>(`http://localhost:8081/Service/save`,ServiceeToAdd)
  }

  public updateServicee (ServiceeToAdd :Servicee) : Observable<Servicee> {
    return  this.http.put<Servicee>(`http://localhost:8081/Service/update/${ServiceeToAdd.uuid}`,ServiceeToAdd)
  }

  public getOneServicee (uuidServicee :String) : Observable<Servicee> {
    return  this.http.get<Servicee>(`http://localhost:8081/Service/${uuidServicee}`)
  }

  public getAllServicee () : Observable<Servicee[]> {
    return  this.http.get<Servicee[]>(`http://localhost:8081/Service/all`)
  }
  

  public deleteServicee (uuidServicee :String) : Observable<any> {
    return  this.http.delete<void>(`http://localhost:8081/Service/delete/${uuidServicee}`)
  }
}
