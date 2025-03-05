import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TypeConge } from '../models/type-conge';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TypeCongeService {

  constructor(private http : HttpClient) {
  }
  
  public newTypeConge (TypeCongeToAdd :TypeConge) : Observable<TypeConge> {
    return  this.http.post<TypeConge>(`http://localhost:8081/TypeConge/save`,TypeCongeToAdd)
  }

  public updateTypeConge (TypeCongeToAdd :TypeConge) : Observable<TypeConge> {
    return  this.http.put<TypeConge>(`http://localhost:8081/TypeConge/update/${TypeCongeToAdd.uuid}`,TypeCongeToAdd)
  }

  public getOneTypeConge (uuidTypeConge :String) : Observable<TypeConge> {
    return  this.http.get<TypeConge>(`http://localhost:8081/TypeCongeService/${uuidTypeConge}`)
  }

  public getAllTypeConge () : Observable<TypeConge[]> {
    return  this.http.get<TypeConge[]>(`http://localhost:8081/TypeConge/all`)
  }
  

  public deleteTypeConge (uuidTypeConge :String) : Observable<any> {
    return  this.http.delete<void>(`http://localhost:8081/TypeConge/delete/${uuidTypeConge}`)
  }
}
