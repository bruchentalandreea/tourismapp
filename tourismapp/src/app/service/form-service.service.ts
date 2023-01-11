import { Injectable } from '@angular/core';
import {  BehaviorSubject,Observable } from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {FormPayload} from '../account-page/form-payload'
@Injectable({
  providedIn: 'root'
})
export class FormServiceService {
  private url = 'http://localhost:8080/user';
  /*
  private userSubject: BehaviorSubject<FormPayload>;
  public formPayload: Observable<FormPayload>;*/

  constructor(private httpClient: HttpClient) { 
    /*
    this.userSubject = new BehaviorSubject<FormPayload>(JSON.parse(localStorage.getItem('user')));
    this.formPayload = this.userSubject.asObservable();
    */
  }



  getInputs(FormPayload: FormPayload): Observable<any>{
    console.log("service service");
    console.log(FormPayload);
    return this.httpClient.post<FormPayload>(this.url + "/test", FormPayload);
  }



  getConfig() {
    console.log("aici");
    return this.httpClient.get<Array<FormPayload>>(this.url + "/add");
  }
}
