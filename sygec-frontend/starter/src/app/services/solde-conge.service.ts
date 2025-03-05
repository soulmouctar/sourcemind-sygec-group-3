import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SoldeConge } from '../models/solde-conge';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SoldeCongeService {

  constructor(private http : HttpClient) {
  }
  
  public newSoldeConge (SoldeCongeToAdd :SoldeConge) : Observable<SoldeConge> {
    return  this.http.post<SoldeConge>(`http://localhost:8081/SoldeConge/save`,SoldeCongeToAdd)
  }

  public updateSoldeConge (SoldeCongeToAdd :SoldeConge) : Observable<SoldeConge> {
    return  this.http.put<SoldeConge>(`http://localhost:8081/SoldeConge/update/${SoldeCongeToAdd.uuid}`,SoldeCongeToAdd)
  }

  public getOneSoldeConge (uuidSoldeConge :String) : Observable<SoldeConge> {
    return  this.http.get<SoldeConge>(`http://localhost:8081/SoldeCongeService/${uuidSoldeConge}`)
  }

  public getAllSoldeConge () : Observable<SoldeConge[]> {
    return  this.http.get<SoldeConge[]>(`http://localhost:8081/SoldeConge/all`)
  }
  

  public deleteSoldeConge (uuidSoldeConge :String) : Observable<any> {
    return  this.http.delete<void>(`http://localhost:8081/SoldeConge/delete/${uuidSoldeConge}`)
  }
}
