import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { WalletPayload } from '../wallet/wallet-payload';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WalletService {

  constructor(private httpClient: HttpClient) {

   }
       
  addWallet(walletPayload : WalletPayload){
    return this.httpClient.post('http://localhost:8080/api/wallet', walletPayload);
  }
  getWallet(username:String): Observable<WalletPayload>{
    return this.httpClient.get<WalletPayload>('http://localhost:8080/api/wallet/get/' + username);
  }

  updateWallet(walletPayload:WalletPayload, amount:number, username: String){
    return this.httpClient.patch('http://localhost:8080/api/wallet/update/' + amount + "/" + username, walletPayload);
  }
 
}
