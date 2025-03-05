import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, tap } from "rxjs";
import { environment } from "../environments/environment";
import { Beneficiaire } from "../app/models/beneficiaire";
import { TokenStorageService } from "./token-storage.service";


const AUTH_API = environment.baseUrl + `/authenticate`;
const httpOptions = {
  headers: new HttpHeaders({ "Content-Type": "application/json" }),
};
@Injectable({
  providedIn: 'root'
})
export class AuthService {


  beneficiaireActuel: Beneficiaire | null = null;
  constructor(private http: HttpClient,
    private tokenStorageService : TokenStorageService
  ) {}
  login(username: string, password: string) {
    console.log(`user: ${username}`);
    return this.http
      .post(AUTH_API, { username, password }, httpOptions)
      .toPromise()
      .then((res) => res);
  }

  register(username: string, email: string, password: string): Observable<any> {
    return this.http.post(
      AUTH_API + "signup",
      {
        username,
        email,
        password,
      },
      httpOptions
    );
  }
  getCurrentBeneficiaire(): Beneficiaire | null {
    return this.beneficiaireActuel;
  }

  // Vous pouvez également ajouter une méthode pour définir le bénéficiaire actuel
  setBeneficiaireActuel(beneficiaire: Beneficiaire): void {
    this.beneficiaireActuel = beneficiaire;
  }

  hasRole(role: string): boolean {
    const userRoles = this.tokenStorageService.getRole();
    return userRoles.includes(role);
  }
  
}
