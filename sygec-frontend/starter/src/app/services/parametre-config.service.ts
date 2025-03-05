import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { ParametreConfig } from '../models/parametre-config';

@Injectable({
  providedIn: 'root'
})
export class ParametresConfigService {
  private apiUrl = `${environment.baseUrl}/api/parametres-config`;
  
  // Valeurs par défaut correspondant aux valeurs du backend
  private defaultConfig: ParametreConfig = {
    dureeMinConge: 1,
    dureeMaxConge: 90,
    delaiMinDemande: 7,
    soldeMinRequis: 0.5
  };

  // BehaviorSubject pour permettre à plusieurs composants de s'abonner aux changements
  private parametresSubject = new BehaviorSubject<ParametreConfig>(this.defaultConfig);
  
  // Observable public pour les abonnements
  public parametres$ = this.parametresSubject.asObservable();

  constructor(private http: HttpClient) {
    // Charger les paramètres au démarrage du service
    this.loadParametres();
  }

  /**
   * Charge les paramètres depuis le backend
   */
  loadParametres(): void {
    this.http.get<ParametreConfig>(this.apiUrl)
      .pipe(
        tap(
          (config) => {
            this.parametresSubject.next(config);
            console.log('Paramètres chargés avec succès', config);
          },
          (error) => {
            console.error('Erreur lors du chargement des paramètres', error);
            // En cas d'erreur, utiliser les valeurs par défaut
            this.parametresSubject.next(this.defaultConfig);
          }
        )
      )
      .subscribe();
  }

  /**
   * Récupère les paramètres actuels
   */
  getParametres(): Observable<ParametreConfig> {
    return this.parametres$;
  }

  /**
   * Met à jour les paramètres
   */
  updateParametres(parametres: ParametreConfig): Observable<ParametreConfig> {
    return this.http.put<ParametreConfig>(this.apiUrl, parametres)
      .pipe(
        tap((updatedConfig) => {
          this.parametresSubject.next(updatedConfig);
        })
      );
  }

  /**
   * Réinitialise les paramètres aux valeurs par défaut
   */
  resetParametres(): Observable<ParametreConfig> {
    return this.http.post<ParametreConfig>(`${this.apiUrl}/reset`, {});
  }
}