import { Injectable, SystemJsNgModuleLoader } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {catchError, map} from 'rxjs/operators';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';


@Injectable({
  providedIn: 'root'
})
export class FileUploadService {


  constructor(private httpClient: HttpClient) { }

  postFile(fileToUpload: File): Observable<boolean> {
    const endpoint = 'http://localhost:4200/add-post';
    const formData: FormData = new FormData();
    formData.append('fileKey', fileToUpload, fileToUpload.name);
    return this.httpClient
      .post(endpoint, formData)
      .pipe(map(() => { return true; }))
      

}

postFile2(fileToUpload: File): Observable<boolean> {
  const endpoint = 'http://localhost:4200/add-holiday';
  const formData: FormData = new FormData();
  formData.append('fileKey', fileToUpload, fileToUpload.name);
  return this.httpClient
    .post(endpoint, formData)
    .pipe(map(() => { return true; }))
    

}
}
