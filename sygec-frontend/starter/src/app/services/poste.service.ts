import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Poste } from '../models/poste';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PosteService {

  constructor(private http : HttpClient) {
  }
  
  public newPoste (PosteToAdd :Poste) : Observable<Poste> {
    return  this.http.post<Poste>(`http://localhost:8081/Poste/save`,PosteToAdd)
  }

  public updatePoste (PosteToAdd :Poste) : Observable<Poste> {
    return  this.http.put<Poste>(`http://localhost:8081/Poste/update/${PosteToAdd.uuid}`,PosteToAdd)
  }

  public getOnePoste (uuidPoste :String) : Observable<Poste> {
    return  this.http.get<Poste>(`http://localhost:8081/PosteService/${uuidPoste}`)
  }

  public getAllPoste () : Observable<Poste[]> {
    return  this.http.get<Poste[]>(`http://localhost:8081/Poste/all`)
  }
  

  public deletePoste (uuidPoste :String) : Observable<any> {
    return  this.http.delete<void>(`http://localhost:8081/Poste/delete/${uuidPoste}`)
  }

  public getAllPosteByServicee (uuidServicee:string) : Observable<Poste[]> {
    return  this.http.get<Poste[]>(`http://localhost:8081/Poste/byServicee/${uuidServicee}`,{
      headers:new HttpHeaders({
        "Content-Type":"application/json"
      })
    })
  }
}
