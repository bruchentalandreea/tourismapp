import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { PostPayload } from '../add-post/post-payload';
import {Observable} from 'rxjs';
import { CategoryPayload } from '../add-trip/category-payload';
import { TripPayload } from '../add-trip/trip-payload';
import { RolePayload } from '../add-trip/role-payload';
import { EventEmitter } from '@angular/core';
@Injectable({
  providedIn: 'root'
})
export class AddPostService {
  constructor(private httpClient: HttpClient) {
  }


  addPost(postPayload: PostPayload){
    return this.httpClient.post('http://localhost:8080/api/posts', postPayload);
  }

  getAvgRating(permaLink: Number): Observable<Number>{
    return this.httpClient.get<Number>('http://localhost:8080/api/posts/get/avg/' + permaLink);
  }

  getListPhoto(permaLink: Number): Observable<Array<String>>{
    return this.httpClient.get<Array<String>>('http://localhost:8080/api/posts/photo/' + permaLink);
  }

  getAllPosts(): Observable<Array<TripPayload>>{
    return this.httpClient.get<Array<TripPayload>>("http://localhost:8080/api/posts/all");
  }

  getPost(permaLink: Number):Observable<TripPayload>{
    return this.httpClient.get<TripPayload>('http://localhost:8080/api/posts/get/' + permaLink);
  }

  getAllPostsByUser(name: string): Observable<Array<PostPayload>> {
    return this.httpClient.get<Array<PostPayload>>('http://localhost:8080/api/posts/by-user/' + name);
  }

  filterPostsByPrice( priceMax: string, priceMin: string): Observable<Array<TripPayload>> {
    return this.httpClient.get<Array<TripPayload>>('http://localhost:8080/api/posts/all/by-price/' + priceMax + '/' + priceMin);
  }

  filterPostsByDate( check_in_date: string, check_out_date: string): Observable<Array<TripPayload>> {
    return this.httpClient.get<Array<TripPayload>>('http://localhost:8080/api/posts/all/by-date/' + check_in_date + '/' + check_out_date);
  }

  filterPostsByLocation( country:string, state:string, city:string): Observable<Array<TripPayload>> {
    return this.httpClient.get<Array<TripPayload>>('http://localhost:8080/api/posts/all/by-location/' + country + '/' + state + '/' + city);
  }

  filterPostsByType(type_of_vacation:string): Observable<Array<TripPayload>> {
    return this.httpClient.get<Array<TripPayload>>('http://localhost:8080/api/posts/all/by-type/' + type_of_vacation);
  }

  filterPostsByNrOfPersons(nr_of_persons): Observable<Array<TripPayload>> {
    return this.httpClient.get<Array<TripPayload>>('http://localhost:8080/api/posts/all/by-persons/' + nr_of_persons);
  }

  getAllCategories(): Observable<Array<CategoryPayload>>{
    return this.httpClient.get<Array<CategoryPayload>>("http://localhost:8080/api/posts/categories");
  }

  getRole(username:string): Observable<string>{
    return this.httpClient.get<string>('http://localhost:8080/api/auth/role/' + username, { responseType: 'text' as 'json' } );
  }

  searchPost(nr_of_persons:number,city: string,type_of_vacation:string): Observable<Array<TripPayload>> {
    return this.httpClient.get<Array<TripPayload>>('http://localhost:8080/api/posts/search/' + nr_of_persons + '/' + city + '/' +  type_of_vacation);
  }

  getRecommendations(username:string): Observable<Array<TripPayload>>{
    return this.httpClient.get<Array<TripPayload>>("http://localhost:8080/api/posts/recommendation/" + username);
  }

  deletePost(id: Number): any{
    console.log("functia delete" + id);

  return this.httpClient.delete("http://localhost:8080/api/posts/delete/" + id);
  }

  getPhoto(id: Number): Observable<String>{
    return this.httpClient.get<String>('http://localhost:8080/api/posts/get/photo/' + id);
  }

  getPrice(id: Number): Observable<number>{
    return this.httpClient.get<number>('http://localhost:8080/api/posts/get/price/' + id);
  }
}
