import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Value } from '../models/value';
import { DemandeConge } from '../models/demande-conge';
import { Position } from 'ngx-perfect-scrollbar';

@Injectable({
  providedIn: 'root'
})
export class DemandeCongeService { 
   private readonly codeDemandeCongeUrl: String =`${environment.baseUrl}/genererCodeDemandeBeneficiaire`

constructor(private http : HttpClient) {
}

public newDemandeConge ( DemandeCongeToAdd : DemandeConge) : Observable< DemandeConge> {
  return  this.http.post< DemandeConge>(`http://localhost:8081/DemandeConge/save`, DemandeCongeToAdd)
}

public updateDemandeConge ( DemandeCongeToAdd : DemandeConge) : Observable< DemandeConge> {
  return  this.http.put< DemandeConge>(`http://localhost:8081/DemandeConge/update/${ DemandeCongeToAdd.uuid}`, DemandeCongeToAdd)
}

public getOneDemandeConge (uuidDemandeConge :String) : Observable< DemandeConge> {
  return  this.http.get< DemandeConge>(`http://localhost:8081/DemandeCongeService/${uuidDemandeConge}`)
}

public getAllDemandeCongefindAllIsValider () : Observable< DemandeConge[]> {
  return  this.http.get< DemandeConge[]>(`http://localhost:8081/DemandeConge/findAllIsValider`)
}


public getAllDemandeCongefindAllIsNotValider () : Observable< DemandeConge[]> {
  return  this.http.get< DemandeConge[]>(`http://localhost:8081/DemandeConge/findAllIsNotValider`)
}


public getAllDemandeCongefindAllIsRejetter () : Observable< DemandeConge[]> {
  return  this.http.get< DemandeConge[]>(`http://localhost:8081/DemandeConge/findAllIsRejetter`)
}


public getAllDemandeCongefindAllIsAnnuler () : Observable< DemandeConge[]> {
  return  this.http.get< DemandeConge[]>(`http://localhost:8081/DemandeConge/findAllIsAnnuler`)
}


public getAllDemandeConge () : Observable< DemandeConge[]> {
  return  this.http.get< DemandeConge[]>(`http://localhost:8081/DemandeConge/all`)
}

public deleteDemandeConge (uuidDemandeConge :String) : Observable<any> {
  return  this.http.delete<void>(`http://localhost:8081/DemandeConge/delete/${uuidDemandeConge}`)
}

getCodeDemandeConge(): Observable<Value> {
  let url = `${this.codeDemandeCongeUrl}`;
  return this.http.get<Value>(url, {
    headers: new HttpHeaders({
      "Content-Type": "application/json",
    }),
  });

}

findAllByBeneficiaire(uuidBeneficiaire: string): Observable<DemandeConge[]> {
  return this.http.get<DemandeConge[]>(`http://localhost:8081/beneficiaire/${uuidBeneficiaire}`);
}

 /**
   * Approuve une demande de congé
   */
 approuver(uuid: string): Observable<DemandeConge> {
  return this.http.put<DemandeConge>(`http://localhost:8081/DemandeConge/${uuid}/approuver`, {});
}

/**
 * Rejette une demande de congé
 */
rejeter(uuid: string): Observable<DemandeConge> {
  return this.http.put<DemandeConge>(`http://localhost:8081/DemandeConge/${uuid}/rejeter`, {});
}

/**
 * Annule une demande de congé
 */
annuler(uuid: string): Observable<DemandeConge> {
  return this.http.put<DemandeConge>(`http://localhost:8081/DemandeConge/${uuid}/annuler`, {});
}




}
