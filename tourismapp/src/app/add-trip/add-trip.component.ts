import { Component, OnInit } from '@angular/core';
import {ThemePalette} from '@angular/material/core';
import {FormControl, FormGroup} from '@angular/forms';
import { LoginService } from '../service/login.service';
import { Router } from '@angular/router';
import { AddPostService } from '../service/add-post.service';
import { Observable } from 'rxjs';
import { MenuService } from '../service/menu.service';
import { TripPayload } from './trip-payload';
import { FeedbackPayload } from '../user-profile/feedback-payload';
import { FeedbackService } from '../service/feedback.service';
import { DatePipe } from '@angular/common';
import { HolidayPayload } from '../add-holiday/holiday-payload';
import { HolidayService } from '../service/holiday.service';
import { MatTable, MatTableDataSource } from '@angular/material/table';
import { pluck, map } from "rxjs/operators";


export interface Task {
  name: string;
  completed: boolean;
  color: ThemePalette;
  subtasks?: Task[];
}

@Component({
  selector: 'app-add-trip',
  templateUrl: './add-trip.component.html',
  styleUrls: ['./add-trip.component.css']
})
export class AddTripComponent implements OnInit {

  posts: Observable<Array<TripPayload>>;
  holidays: Observable<Array<HolidayPayload>>;

  addFeedbackForm:FormGroup;
  priceMin: string ='';
  priceMax: string ='';
  check_in_date:string='';
  check_out_date:string='';
  contentEditable:boolean;

  country: string='';
  state: string='';
  city: string='';
  photo:string;

  currentRole:string;

  public edited = false;


  feedbackPayload: FeedbackPayload;
  permaLink: Number =1;
  currentRate_post: Number = 3.14;
  currentRate_holiday: Number = 3.14;
post_id= new FormControl('');
rating= new FormControl('');
user= new FormControl('');

currentIdPost : String;
currentIdHoliday: String;
currentRating: String;
currentUsername: string;
count:number = 0;
rez:String;
role:String;

nr_of_persons:String = '';
public selectedType: string= '';

//filters////
nameFilter = new FormControl();
dataSource = new MatTableDataSource();

public price_invalid:boolean;
public location_invalid:boolean;
public dates_invalid:boolean;
public type_invalid:boolean;
public persons_invalid:boolean;

  constructor( private holidayService: HolidayService, public menuService:MenuService,private feedbackService: FeedbackService, public loginService:LoginService, private router:Router, private addPostService: AddPostService) {
    this.currentUsername = this.loginService.getUserName();
  

    this.addFeedbackForm= new FormGroup({
      post_id:this.post_id,
      rating:this.rating,
      user:this.user
    });

    this.feedbackPayload =
    {
     feedback_id:'',
     post_id:'',
     holiday_id:'',
     rating:'',
     username:''
    }
   }

  ngOnInit(): void {
  


      this.addPostService.getRole(this.currentUsername).subscribe((data:String) => {
        this.role=data;
        console.log(data);

        console.log(this.role + "REZULTATUL");
        if(this.role =="company" || this.role=="seller"){
              //show box msg
          this.edited = true;
        }
        else {
          //wait 3 Seconds and hide
          setTimeout(function() {
              this.edited = false;
              console.log(this.edited);
          }.bind(this), 3000);
      
         }

      },(err: any) => {
        console.log('Failure Response');
      })


    

    this.posts = this.addPostService.getAllPosts();
    


      


    this.holidays = this.holidayService.getAllHolidays();


    this.holidayService.getAvgRating(this.permaLink).subscribe((data:Number) => {
      this.currentRate_holiday = data;
      
    },(err: any) => {
      console.log('Failure Response');
    })

 
  }

  task: Task = {
    name: 'Indeterminate',
    completed: false,
    color: 'primary',
    subtasks: [
      {name: 'Primary', completed: false, color: 'primary'},
      {name: 'Accent', completed: false, color: 'accent'},
      {name: 'Warn', completed: false, color: 'warn'}
    ]
  };

  allComplete: boolean = false;

  updateAllComplete() {
    this.allComplete = this.task.subtasks != null && this.task.subtasks.every(t => t.completed);
  }

  someComplete(): boolean {
    if (this.task.subtasks == null) {
      return false;
    }
    return this.task.subtasks.filter(t => t.completed).length > 0 && !this.allComplete;
  }

  setAll(completed: boolean) {
    this.allComplete = completed;
    if (this.task.subtasks == null) {
      return;
    }
    this.task.subtasks.forEach(t => t.completed = completed);
  }

  filterPostsByPrice()
  {
    if (this.priceMax != ''&& this.priceMin != '') {
    this.posts = this.addPostService.filterPostsByPrice(this.priceMax,this.priceMin);
    this.price_invalid=false;
  }
  else{
    this.price_invalid= true;
  }
  }

  filterPostsByDate()
  {
    if (this.check_in_date != '' && this.check_out_date != '') {
    this.dates_invalid=false;
    this.posts = this.addPostService.filterPostsByDate(this.check_in_date,this.check_out_date);
    }
    else{
      this.dates_invalid=true;
    }
  }

  filterPostsByLocation()
  {
    if (this.country != '' && this.state != '' && this.city  != '') {
    this.location_invalid=false;
    this.posts = this.addPostService.filterPostsByLocation(this.country,this.state,this.city);
    this.holidays = this.holidayService.filterHolidaysByLocation(this.country,this.state,this.city);
    }
    else{
      this.location_invalid=true;
    }
  }

  filterPostsByType()
  {
    if(this.selectedType !=''){
    this.type_invalid=false;
    this.posts = this.addPostService.filterPostsByType(this.selectedType);
    this.holidays = this.holidayService.filterHolidaysByType(this.selectedType);
    }
    else{
      this.type_invalid=true;
    }
  }

  filterPostsByNrOfPersons()
  {
    if(this.nr_of_persons !=''){
      this.persons_invalid=false;
    this.posts = this.addPostService.filterPostsByNrOfPersons(this.nr_of_persons);
    this.holidays = this.holidayService.filterHolidaysByNrOfPersons(this.nr_of_persons);
    }
    else{
      this.persons_invalid=true;
    }
  }

  deletePost(id)
  {
   this.posts =  this.addPostService.deletePost(id);
    window.location.reload();
    console.log("Deleted post" + id );
  }

  deleteHoliday(id)
  {
   this.holidays =  this.holidayService.deleteHoliday(id);
    window.location.reload();
    console.log("Deleted holiday" + id );
  }

  toggleEditable(event) {
    if ( event.target.checked ) {
        this.contentEditable = true;
   }
}



  public switchType(type: string): string{
    this.selectedType = type;

    return type;
  }

  public singleHoliday(id){
    var myDivID = '../single-holiday/' + id;
    window.location.href=myDivID ;   
  }

 
  public singlePost(id){
    var myDivID = '../single-post/' + id;
    window.location.href=myDivID ;   
  }


  getRating(id){
    this.addPostService.getAvgRating(id).subscribe((data:Number) => {
      this.currentRate_post = data;
      console.log("RATING" + this.currentRate_post )
    },(err: any) => {
      console.log('Failure Response');
    })
  }


  customFilterPredicate() {
    const myFilterPredicate = function(filter:string, data:String) :boolean {
      let searchString = JSON.parse(filter);
      let nameFound = data.toString().trim().toLowerCase().indexOf(searchString.name.toLowerCase()) !== -1
      let positionFound = data.toString().trim().indexOf(searchString.position) !== -1
      let weightFound = data.toString().trim().indexOf(searchString.weight) !== -1
      if (searchString.topFilter) {
          return nameFound || positionFound || weightFound
      } else {
          return nameFound && positionFound && weightFound
      }
    }
    return myFilterPredicate;
  }

  getPostsByCity(city: String) {
    return this.posts.subscribe(posts => posts.filter(posts => this.posts[9] === city));
  }



}
