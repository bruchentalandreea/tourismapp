import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {FormPayload} from '../account-page/form-payload'
import { UserProfilePayload } from '../user-profile/user-profile-payload';
@Injectable({
  providedIn: 'root'
})
export class AccountPageService {
  private url = 'http://localhost:8080/api/auth/';
  constructor(private httpClient:HttpClient) { }

  register(formPayload:FormPayload) : Observable<any>
  {
    return this.httpClient.post(this.url + 'signup', formPayload);
  }

  update(userProfilePayload:UserProfilePayload, username: string){
    return this.httpClient.post('http://localhost:8080/api/auth/profile/' + username, userProfilePayload);
  }

  getUser(username: string):Observable<UserProfilePayload>{
    return this.httpClient.get<UserProfilePayload>('http://localhost:8080/api/auth/get-user/' + username);
  }

  updatePhone(userProfilePayload:UserProfilePayload, phone_number:String, username: string){
    return this.httpClient.patch('http://localhost:8080/api/auth/phone/' + phone_number + "/" + username, userProfilePayload);
  }
  
  updateName(userProfilePayload:UserProfilePayload, name:String, username: string){
    return this.httpClient.patch('http://localhost:8080/api/auth/name/' + name + "/" + username, userProfilePayload);
  }

  updateSurname(userProfilePayload:UserProfilePayload, surname:String, username: string){
    return this.httpClient.patch('http://localhost:8080/api/auth/surname/' + surname + "/" + username, userProfilePayload);
  }

  updateAddress(userProfilePayload:UserProfilePayload, country:String,state:String,city:String, username: string){
    return this.httpClient.patch('http://localhost:8080/api/auth/address/' + country + "/" + state + "/" + city + "/" + username, userProfilePayload);
  }
}
