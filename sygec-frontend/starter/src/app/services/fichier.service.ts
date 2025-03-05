import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class FichierService {
  SaveImageAsDataUrl(arg0: any) {
    throw new Error('Method not implemented.');
  }

  private readonly _fichierNameUrl: string = `${environment.baseUrl}` + `/upload`;
  private readonly _fichierDownNameUrl: string = `${environment.baseUrl}` + `/files`;
  private readonly _imgUploadNameUrl: string = `${environment.baseUrl}` + `/uploadImgAsDataUrl`;
  private readonly _imgDownloadNameUrl: string = `${environment.baseUrl}` + `/downloadImgAsDataUrl`;

  constructor(private http: HttpClient) { }

  SaveFile(file: File): Observable<File> {
    const formData: FormData = new FormData();

    formData.append('file', file);
    return this.http.post<File>(this._fichierNameUrl, formData, {
      headers: new HttpHeaders({
      })
    });
  }
  

  SaveImgAsDataUrl(dataUrl: String): Observable<File> {
    return this.http.post<any>(this._imgUploadNameUrl, dataUrl, {
      headers: new HttpHeaders({
      })
    });
  }

  getFile(id: string): any {
    let url = `${this._fichierDownNameUrl}` + `/${id}`;
    return this.http.get<any>(url);
  }

  getImgAsDataUrl(id: string): Observable<any> {
    let url = `${this._imgDownloadNameUrl}` + `/${id}`;
    return this.http.get<any>(url);
  }
}
