import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Beneficiaire } from '../models/beneficiaire';


@Injectable({
  providedIn: 'root'
})
export class BeneficiaireService {

  constructor(private http : HttpClient) {
  }
  
  public newBeneficiaire (beneficiaireToAdd :Beneficiaire) : Observable<Beneficiaire> {
    return  this.http.post<Beneficiaire>(`http://localhost:8081/Beneficiaire/save`,beneficiaireToAdd)
  }

  public updateBeneficiaire (beneficiaireToAdd :Beneficiaire) : Observable<Beneficiaire> {
    return  this.http.put<Beneficiaire>(`http://localhost:8081/Beneficiaire/update/${beneficiaireToAdd.uuid}`,beneficiaireToAdd)
  }

  public getOneBeneficiaire (uuidBeneficiaire :String) : Observable<Beneficiaire> {
    return  this.http.get<Beneficiaire>(`http://localhost:8081/BeneficiaireService/${uuidBeneficiaire}`)
  }

  public getAllBeneficiaire () : Observable<Beneficiaire[]> {
    return  this.http.get<Beneficiaire[]>(`http://localhost:8081/Beneficiaire/all`)
  }
  

  public deleteBeneficiaire (uuidBeneficiaire :String) : Observable<any> {
    return  this.http.delete<void>(`http://localhost:8081/Beneficiaire/delete/${uuidBeneficiaire}`)
  }
}
