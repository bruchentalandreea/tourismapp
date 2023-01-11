import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { HolidayPayload } from '../add-holiday/holiday-payload';
import { Observable } from 'rxjs';
import { GraphicPayload } from '../line-chart/graphicPayload';

@Injectable({
  providedIn: 'root'
})
export class HolidayService {

  constructor(private httpClient: HttpClient) {
  }

  addHoliday(holidayPayload: HolidayPayload){
    return this.httpClient.post('http://localhost:8080/api/holidays', holidayPayload);
  }

  getAllHolidays(): Observable<Array<HolidayPayload>>{
    return this.httpClient.get<Array<HolidayPayload>>("http://localhost:8080/api/holidays/all");
  }

  getHoliday(permaLink: Number):Observable<HolidayPayload>{
    return this.httpClient.get<HolidayPayload>('http://localhost:8080/api/holidays/get/' + permaLink);
  }

  getAvgRating(permaLink: Number): Observable<Number>{
    return this.httpClient.get<Number>('http://localhost:8080/api/holidays/get/avg/' + permaLink);
  }

  getListPhoto(permaLink: Number): Observable<Array<String>>{
    return this.httpClient.get<Array<String>>('http://localhost:8080/api/holidays/photo/' + permaLink);
  }


  getPrice(id: Number): Observable<number>{
    return this.httpClient.get<number>('http://localhost:8080/api/holidays/get/price/' + id);
  }

  getPhoto(id: Number): Observable<String>{
    return this.httpClient.get<String>('http://localhost:8080/api/holidays/get/photo/' + id);
  }

  deleteHoliday(id: Number): any{
    console.log("Delted holiday" + id);

  return this.httpClient.delete("http://localhost:8080/api/holidays/delete/" + id);
  }

  getNrOfHolidays(username:String): Observable<Array<GraphicPayload>>{
    return this.httpClient.get<Array<GraphicPayload>>("http://localhost:8080/api/holidays/size/" + username);
  }

  filterHolidaysByPrice( priceMax: string, priceMin: string): Observable<Array<HolidayPayload>> {
    return this.httpClient.get<Array<HolidayPayload>>('http://localhost:8080/api/holidays/all/by-price/' + priceMax + '/' + priceMin);
  }


  filterHolidaysByLocation( country:string, state:string, city:string): Observable<Array<HolidayPayload>> {
    return this.httpClient.get<Array<HolidayPayload>>('http://localhost:8080/api/holidays/all/by-location/' + country + '/' + state + '/' + city);
  }

  filterHolidaysByType(type_of_vacation:string): Observable<Array<HolidayPayload>> {
    return this.httpClient.get<Array<HolidayPayload>>('http://localhost:8080/api/holidays/all/by-type/' + type_of_vacation);
  }

  filterHolidaysByNrOfPersons(nr_of_persons): Observable<Array<HolidayPayload>> {
    return this.httpClient.get<Array<HolidayPayload>>('http://localhost:8080/api/holidays/all/by-persons/' + nr_of_persons);
  }

  getNrOfHolidaysByCountry(country: String): Observable<Array<GraphicPayload>>{
    return this.httpClient.get<Array<GraphicPayload>>('http://localhost:8080/api/holidays/size/by-country/' + country);
  }

  getRecommendations(username:string): Observable<Array<HolidayPayload>>{
    return this.httpClient.get<Array<HolidayPayload>>("http://localhost:8080/api/holidays/recommendation/" + username);
  }
}
