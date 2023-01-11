import { Component} from '@angular/core';
import { CalendarService} from '../service/calendar.service'
import { GuestsService} from '../service/guests.service'
import { Router } from '@angular/router';
import { FormGroup, FormBuilder } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { LoginService } from '../service/login.service';
import { Observable } from 'rxjs';
import { TripPayload } from '../add-trip/trip-payload';
import { AddPostService } from '../service/add-post.service';
import { ShareDataService } from '../service/share-data.service';
import { Store } from '@ngrx/store';
import { LocalStorageService } from 'ngx-webstorage';
import { HolidayPayload } from '../add-holiday/holiday-payload';
import { HolidayService } from '../service/holiday.service';
import { NgbCarousel, NgbSlideEvent, NgbSlideEventSource } from '@ng-bootstrap/ng-bootstrap';
import { ViewChild } from '@angular/core';
import { map, filter, scan } from 'rxjs/operators';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent  {
  


  registerForm: FormGroup;

  city_search:string;
  nr_of_persons:number;
  type_of_vacation:string;

  posts: Observable<Array<TripPayload>>;
  holidays: Observable<Array<HolidayPayload>>;
  text='';
  username:string;

 
  constructor(private holidayService:HolidayService, private $localStorage: LocalStorageService,private store:Store, private sharedDataService:ShareDataService, private addPostService: AddPostService, private formBuilder: FormBuilder, private http: HttpClient,private router:Router, public calendarService:CalendarService, public guestsService:GuestsService, private loginService:LoginService){
    this.registerForm=this.formBuilder.group( {username : '' });


  }
  ngOnInit() {
    this.posts = this.addPostService.getAllPosts();
    this.holidays = this.holidayService.getAllHolidays();

    this.username = this.loginService.getUserName();
    this.posts =this.addPostService.getRecommendations(this.username);
    console.log(localStorage.getItem('dataSource'));
 
  }

search(){
  this.posts = this.addPostService.searchPost(this.nr_of_persons,this.city_search,this.type_of_vacation);
}

public onInput(event: any){
  // this pushes the input value into the service's Observable.
  this.sharedDataService.searchTerm.next(event.target.value);
  localStorage.setItem('dataSource', event);
}

public go(city_search,nr_of_persons,type_of_vacation){
  window.location.href='../search-results';
  localStorage.setItem('city_search', city_search);
  localStorage.setItem('nr_of_persons', nr_of_persons);
  localStorage.setItem('type_of_vacation', type_of_vacation);
}


find(){
  window.location.href='../add-trip';
}
}



