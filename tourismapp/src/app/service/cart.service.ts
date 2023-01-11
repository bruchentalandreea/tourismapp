import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import { CartPayload } from '../cart/cartPayload';



@Injectable({
  providedIn: 'root'
})
export class CartService {

  constructor(private httpClient: HttpClient) { }

  addCart(cartPayload: CartPayload){
    return this.httpClient.post('http://localhost:8080/api/cart', cartPayload);
  }

  getCart(permaLink: Number):Observable<CartPayload>{
    return this.httpClient.get<CartPayload>('http://localhost:8080/api/cart/get/' + permaLink);
  }
}
