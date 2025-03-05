import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Beneficiaire } from '../models/beneficiaire';
import { Poste } from '../models/poste';
import { Servicee } from '../models/servicee';
import { TypeConge } from '../models/type-conge';
import { SoldeConge } from '../models/solde-conge';

@Injectable({
  providedIn: 'root'
})
export class ReferenceDataService {

  private apiUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  /**
   * Récupère tous les bénéficiaires
   */
  getAllBeneficiaires(): Observable<Beneficiaire[]> {
    return this.http.get<Beneficiaire[]>(`${this.apiUrl}/api/beneficiaires`);
  }

  /**
   * Récupère un bénéficiaire par son identifiant
   */
  getBeneficiaireById(uuid: string): Observable<Beneficiaire> {
    return this.http.get<Beneficiaire>(`${this.apiUrl}/api/beneficiaires/${uuid}`);
  }

  /**
   * Récupère tous les postes
   */
  getAllPostes(): Observable<Poste[]> {
    return this.http.get<Poste[]>(`${this.apiUrl}/api/postes`);
  }

  /**
   * Récupère tous les services
   */
  getAllServices(): Observable<Servicee[]> {
    return this.http.get<Servicee[]>(`${this.apiUrl}/api/services`);
  }

  /**
   * Récupère tous les types de congé
   */
  getAllTypesConge(): Observable<TypeConge[]> {
    return this.http.get<TypeConge[]>(`${this.apiUrl}/api/types-conge`);
  }

  /**
   * Récupère le solde de congé d'un bénéficiaire pour une année donnée
   */
  getSoldeCongeByBeneficiaireAndAnnee(beneficiaireUuid: string, annee: number): Observable<SoldeConge> {
    return this.http.get<SoldeConge>(`${this.apiUrl}/api/solde-conges/beneficiaire/${beneficiaireUuid}/annee/${annee}`);
  }

  /**
   * Récupère tous les soldes de congé d'un bénéficiaire
   */
  getAllSoldeCongeByBeneficiaire(beneficiaireUuid: string): Observable<SoldeConge[]> {
    return this.http.get<SoldeConge[]>(`${this.apiUrl}/api/solde-conges/beneficiaire/${beneficiaireUuid}`);
  }
}
