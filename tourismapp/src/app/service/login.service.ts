import { Injectable,Output, EventEmitter  } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginPayload } from '../login-page/login-payload';
import {JwtLoginResponse} from '../login-page/jwt-login-response';
import {map} from 'rxjs/operators';
import {LocalStorageService} from 'ngx-webstorage';


@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private url = 'http://localhost:8080/api/auth/';
  @Output() username: EventEmitter<string> = new EventEmitter(); 


  refreshTokenPayload = {
    refreshToken: this.getRefreshToken(),
    username: this.getUserName()
  } 

  constructor(private httpClient: HttpClient, private localStoraqeService: LocalStorageService) {
  }

  login(loginPayload: LoginPayload): Observable<boolean> {
    return this.httpClient.post<JwtLoginResponse>(this.url + 'login', loginPayload).pipe(map(data => {
      this.localStoraqeService.store('authenticationToken', data.authenticationToken);
      this.localStoraqeService.store('username', data.username);
      
      this.username.emit(data.username);
      return true;
    }));
  }

  isAuthenticated(): Boolean {
    console.log("isAuthenticated");
    return this.localStoraqeService.retrieve('username') != null;
  }



  logout() {
    this.localStoraqeService.clear('authenticationToken');
    this.localStoraqeService.clear('username');
  }

  getRefreshToken() {
    return this.localStoraqeService.retrieve('refreshToken');
  }


  getUserName() {
    return this.localStoraqeService.retrieve('username');
  }

  addEmail(email:String): Observable<any>{
    console.log("service");
    return this.httpClient.post(this.url + "email",email);
  }



}
