import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { BookingsPayload } from '../bookings/bookings-payload';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class BookingsService {
  constructor(private httpClient: HttpClient) {
  }

  addBooking(bookingsPayload: BookingsPayload){
    return this.httpClient.post('http://localhost:8080/api/bookings', bookingsPayload);
  }

  getBooking(permaLink: Number):Observable<BookingsPayload>{
    return this.httpClient.get<BookingsPayload>('http://localhost:8080/api/bookings/get/' + permaLink);
  }

  getBookingLast():Observable<BookingsPayload>{
    return this.httpClient.get<BookingsPayload>('http://localhost:8080/api/bookings/last');
  }

  deleteBooking(id: Number): any{
    console.log("functia delete" + id);

  return this.httpClient.delete("http://localhost:8080/api/bookings/delete/" + id);
  }

  getBookingsByUsernamePost(username:String):Observable<Array<BookingsPayload>>{
    return this.httpClient.get<Array<BookingsPayload>>('http://localhost:8080/api/bookings/by-user/post/' + username);
  }

  getBookingsByUsernameHoliday(username:String):Observable<Array<BookingsPayload>>{
    return this.httpClient.get<Array<BookingsPayload>>('http://localhost:8080/api/bookings/by-user/holiday/' + username);
  }
}
