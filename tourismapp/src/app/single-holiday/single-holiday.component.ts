import { Component, OnInit } from '@angular/core';
import { HolidayPayload } from '../add-holiday/holiday-payload';
import { ActivatedRoute} from '@angular/router';
import { AddPostService } from '../service/add-post.service';
import { LoginService } from '../service/login.service';
import { TripPayload } from '../add-trip/trip-payload';
import { FormGroup,FormControl } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { HolidayService } from '../service/holiday.service';
import { BookingsService } from '../service/bookings.service';
import { BookingsPayload } from '../bookings/bookings-payload';
import { Router } from '@angular/router';
import { FeedbackPayload } from '../user-profile/feedback-payload';
import { FeedbackService } from '../service/feedback.service';

@Component({
  selector: 'app-single-holiday',
  templateUrl: './single-holiday.component.html',
  styleUrls: ['./single-holiday.component.css']
})
export class SingleHolidayComponent implements OnInit {
  holiday: HolidayPayload;
  addDateForm: FormGroup;
  permaLink: Number;
  currentRate: Number = 3.14;
  check_in_date_holiday = new Date();
  check_out_date_holiday = new Date();
  listPhoto :String[];
  listPhotoElem1:String;
  listPhotoElem2:String;
  listPhotoElem3:String;
  created_on_new:String;
  updated_on_new:String;
  currentPrice:number = 0;
  qty_persons:number = 1;
  price_holiday:number;
  nights:number=0;
  time:number=0;
  totalPrice:number;

  date_in= new Date();
  date_out=new Date();

  // Bookings//
  addBookingsForm: FormGroup;
  bookingsPayload: BookingsPayload;
  title = new FormControl('');
  bookedBy= new FormControl('');
  price= new FormControl('');
  country= new FormControl('');
  state= new FormControl('');
  city= new FormControl('');
  check_in_date= new FormControl('');
  check_out_date= new FormControl('');
  nr_of_persons= new FormControl('');
  type_of_vacation= new FormControl('');
  ///////////

  public alert:boolean = false;
  currentUsername:String;
  photo_id_holiday :String;

  public login_invalid:boolean;

  get_show_stars:string='';
  public show_stars:boolean=false;

  selectedRating = 0;
  stars = [
    {
      id: 1,
      icon: 'star',
      class: 'star-gray star-hover star'
    },
    {
      id: 2,
      icon: 'star',
      class: 'star-gray star-hover star'
    },
    {
      id: 3,
      icon: 'star',
      class: 'star-gray star-hover star'
    },
    {
      id: 4,
      icon: 'star',
      class: 'star-gray star-hover star'
    },
    {
      id: 5,
      icon: 'star',
      class: 'star-gray star-hover star'
    }

  ];
  currentIdPost : String;
  currentRating: String;
  count:number = 0;
  addFeedbackForm:FormGroup;
  post_id= new FormControl('');
  rating= new FormControl('');
  user= new FormControl('');
  feedbackPayload: FeedbackPayload;
  permaLink_new: Number =1;

  constructor( private feedbackService: FeedbackService, private router: Router ,private routerActivated:ActivatedRoute, private holidayService:HolidayService, public loginService:LoginService,private bookingsService :BookingsService) {

    this.holiday = {
      id: '',
      content: '',
      title: '',
      username: '',
      price:'',
      created_on:'',
      updated_on:'',
      country:'',
      state:'',
      city:'',
      avgRating:'',
      photoId:'',
      list_photo_id:'',
      nr_of_persons:'',
      type_of_vacation:''
    }

    this.addBookingsForm = new FormGroup({
      title: this.title,
      bookedBy:this.bookedBy,
      price:this.price,
      country:this.country,
      state:this.state,
      city:this.city,
      check_in_date:this.check_in_date,
      check_out_date:this.check_out_date,
      nr_of_persons:this.nr_of_persons,
      type_of_vacation:this.type_of_vacation

    });
    this.bookingsPayload = {
      id:'',
      title: '',
      bookedBy:'',
      price:'',
      country:'',
      state:'',
      city:'',
      photoId:'',
      check_in_date:new Date(),
      check_out_date:new Date(),
      nr_of_persons:'',
      type_of_vacation:'',
      holiday_id:'',
      post_id:''
    }

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

  ngOnInit() {

    
    this.get_show_stars =  localStorage.getItem('show_stars');
    console.log('stars'+ this.get_show_stars)
    if(this.get_show_stars!=null && this.get_show_stars!='')
    {
      this.show_stars=true;
     
    }
    localStorage.setItem('show_stars','');
    
    this.date_in = this.check_in_date_holiday;
    this.date_out = this.check_out_date_holiday;

    console.log(this.check_in_date_holiday  + "DATA")

    this.qty_persons = 1;
    
    this.routerActivated.params.subscribe(params=> {
      this.permaLink = params['id'];
    });

    this.holidayService.getHoliday(this.permaLink).subscribe((data:HolidayPayload) => {
      this.holiday = data;
    },(err: any) => {
      console.log('Failure Response');
    })

    this.holidayService.getAvgRating(this.permaLink).subscribe((data:Number) => {
      this.currentRate = data;
    },(err: any) => {
      console.log('Failure Response');
    })
 
    this.holidayService.getPrice(this.permaLink).subscribe((data:number) => {
      this.price_holiday = data;
    },(err: any) => {
      console.log('Failure Response');
    })
    
    this.currentUsername = this.loginService.getUserName();

    this.holidayService.getListPhoto(this.permaLink).subscribe((data:String[]) => {
    this.listPhoto = data;
    this.listPhotoElem1= this.listPhoto[0];
    this.listPhotoElem2= this.listPhoto[1];
    this.listPhotoElem3= this.listPhoto[2];

    this.listPhoto.forEach((c) => this.listPhotoElem1 = c); 
  
    console.log("i=" + this.listPhotoElem1);
    },(err: any) => {
      console.log('Failure Response');
    })

   // console.log("created"+ this.holiday.created_on.toString());

   this.holidayService.getPhoto(this.permaLink).subscribe((data:String) => {
    this.photo_id_holiday= data;
  },(err: any) => {
    console.log('Failure Response');
  })
  }


  


  ngAfterContentChecked(){
 

    this.currentPrice = Math.round((this.price_holiday * this.qty_persons) * 100) / 100;

    this.time = ((new Date(this.check_out_date_holiday).getTime())) - ((new Date(this.check_in_date_holiday). getTime()));
    this.nights = this.time / (1000 * 3600 * 24); //Diference in Days.

    if(this.check_in_date_holiday  != this.date_in, this.check_out_date_holiday != this.date_out){
    this.totalPrice = this.currentPrice*this.nights;
  }
   
  }



  addBookings() {

    this.bookingsPayload.title = this.holiday.title;
    this.bookingsPayload.price = this.totalPrice.toString();
    this.bookingsPayload.bookedBy = this.currentUsername;
    this.bookingsPayload.check_in_date= this.check_in_date_holiday;
    this.bookingsPayload.check_out_date=this.check_out_date_holiday;
    this.bookingsPayload.country = this.holiday.country;
    this.bookingsPayload.state = this.holiday.state;
    this.bookingsPayload.city = this.holiday.city;
    this.bookingsPayload.type_of_vacation = this.holiday.type_of_vacation
    this.bookingsPayload.photoId = this.photo_id_holiday;
    this.bookingsPayload.nr_of_persons = this.qty_persons.toString();
    this.bookingsPayload.holiday_id=this.holiday.id.toString();
    this.bookingsPayload.post_id='-1';

    alert("Booking created successfully");  

    console.log("title boo"+ this.bookingsPayload.title + this.bookingsPayload.check_in_date);
  
    this.bookingsService.addBooking(this.bookingsPayload).subscribe(data => {
      alert("Succes creare Booking ");
      this.router.navigateByUrl('/add-trip');
    }, error => {
      console.log('Failure Response');
    })
  
  }

  go(){

    if(this.check_in_date_holiday  != this.date_in && this.check_out_date_holiday != this.date_out)
    {
      if(!this.loginService.isAuthenticated())
      {
        this.login_invalid=true;
      }
      else{
        this.login_invalid=false;
        var myDivID = '../bookings/' + this.permaLink;
        window.location.href=myDivID ;   
        localStorage.setItem('id-booking', this.holiday.id.toString());
        this.addBookings();
      }
  
    }
    else {
      this.alert = true;
    }
    
  }

  addFeedbackHoliday(){
    
    this.feedbackPayload.holiday_id=this.currentIdPost;
    this.feedbackPayload.rating=this.currentRating;
    this.feedbackPayload.username=this.currentUsername;
    console.log("CUrrent post ID"+ this.feedbackPayload.holiday_id);
    this.feedbackService.addFeedbackHoliday(this.feedbackPayload).subscribe(data => {
      console.log("Succes creare post");
    }, error => {
      console.log('Failure Response');
    })
  
  
  }
  
  selectStar(value,post_id,username): void{

    // prevent multiple selection
    if ( this.selectedRating === 0){
  
      this.stars.filter( (star) => {
  
        if ( star.id <= value){
  
          star.class = 'star-gold star';
          this.count ++;
        }else{
  
          star.class = 'star-gray star';
          this.count ++;
        }
        console.log(this.count);
        if(this.count==5){    
        console.log(value);
       
        this.currentIdPost=post_id;
        console.log("Post ID"+ post_id);
        console.log("Current post ID"+ this.currentIdPost);
        this.currentRating=value;
        this.currentUsername=username;
        this.addFeedbackHoliday();
        this.count=0;}
        return star;
      });
  
    }
  
    this.selectedRating = value;
  
  }
}
