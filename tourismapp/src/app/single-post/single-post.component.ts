import { Component, OnInit } from '@angular/core';
import { ActivatedRoute} from '@angular/router';
import { AddPostService } from '../service/add-post.service';
import { LoginService } from '../service/login.service';
import { TripPayload } from '../add-trip/trip-payload';
import { FormGroup,FormControl } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { FeedbackPayload } from '../user-profile/feedback-payload';
import { FeedbackService } from '../service/feedback.service';
import { BookingsService } from '../service/bookings.service';
import { BookingsPayload } from '../bookings/bookings-payload';
import { Router } from '@angular/router';

@Component({
  selector: 'app-single-post',
  templateUrl: './single-post.component.html',
  styleUrls: ['./single-post.component.css']
})
export class SinglePostComponent implements OnInit {
  post: TripPayload;
  addDateForm: FormGroup;
  permaLink: Number;
  currentRate: Number = 3.14;
  listPhoto :String[];
  listPhotoElem1:String;
  listPhotoElem2:String;
  listPhotoElem3:String;
  created_on_new:String;
  updated_on_new:String;
  photo_id_post:String;
  public alert:boolean = false;
  public login_invalid:boolean;
  qty_persons:number = 1;
  selectedRating = 0;
  price_post:number;
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
  currentUsername:String;


  
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

  totalPrice:number = 0;
  constructor(private router_simple: Router, private bookingsService:BookingsService, private feedbackService:FeedbackService, private router:ActivatedRoute, private addpostService:AddPostService, public loginService:LoginService) {
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

    this.post = {
      id: '',
      content: '',
      title: '',
      username: '',
      price: 0,
      created_on:'',
      updated_on:'',
      check_in_date:'',
      check_out_date:'',
      country:'',
      state:'',
      city:'',
      avgRating:'',
      photoId:'',
      list_photo_id:'',
      nr_of_persons:'',
      type_of_vacation:''

      
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
    this.qty_persons = 1;
    
    this.router.params.subscribe(params=> {
      this.permaLink = params['id'];
    });

    this.addpostService.getPost(this.permaLink).subscribe((data:TripPayload) => {
      this.post = data;
    },(err: any) => {
      console.log('Failure Response');
    })
    this.currentUsername = this.loginService.getUserName();

    this.addpostService.getAvgRating(this.permaLink).subscribe((data:Number) => {
      this.currentRate = data;
    },(err: any) => {
      console.log('Failure Response');
    })

    this.addpostService.getListPhoto(this.permaLink).subscribe((data:String[]) => {
    this.listPhoto = data;
    this.listPhotoElem1= this.listPhoto[0];
    this.listPhotoElem2= this.listPhoto[1];
    this.listPhotoElem3= this.listPhoto[2];

    this.listPhoto.forEach((c) => this.listPhotoElem1 = c); 
  
    console.log("i=" + this.listPhotoElem1);
    },(err: any) => {
      console.log('Failure Response');
    })

    console.log("created"+ this.post.created_on.toString());

    this.addpostService.getPhoto(this.permaLink).subscribe((data:String) => {
      this.photo_id_post= data;
    },(err: any) => {
      console.log('Failure Response');
    })

    this.addpostService.getPrice(this.permaLink).subscribe((data:number) => {
      this.price_post = data;
    },(err: any) => {
      console.log('Failure Response');
    })
  }


  setDateTime(dateTime) {
    let pipe = new DatePipe('en-US');

    const time = pipe.transform(dateTime, 'mediumTime', 'UTC');

    const date = pipe.transform(dateTime, 'MM/dd/yyyy', 'UTC');

    return date + ' ' + time;
  }

  
  addFeedback(){
    
    this.feedbackPayload.post_id=this.currentIdPost;
    this.feedbackPayload.rating=this.currentRating;
    this.feedbackPayload.username=this.currentUsername;
    console.log("CUrrent post ID"+ this.feedbackPayload.post_id);
    this.feedbackService.addFeedback(this.feedbackPayload).subscribe(data => {
      console.log("Succes creare Feedback");
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
        this.addFeedback();
        this.count=0;}
        return star;
      });
  
    }
  
    this.selectedRating = value;
  
  }

  addBookings() {

    this.bookingsPayload.title = this.post.title;
    this.bookingsPayload.price = this.post.price.toString();
    this.bookingsPayload.bookedBy = this.currentUsername;
    this.bookingsPayload.check_in_date= new Date(this.post.check_in_date.toString());
    this.bookingsPayload.check_out_date=new Date(this.post.check_out_date.toString());
    this.bookingsPayload.country = this.post.country;
    this.bookingsPayload.state = this.post.state;
    this.bookingsPayload.city = this.post.city;
    this.bookingsPayload.type_of_vacation = this.post.type_of_vacation
   this.bookingsPayload.photoId = this.photo_id_post
    this.bookingsPayload.nr_of_persons = this.post.nr_of_persons;
    this.bookingsPayload.holiday_id='-1';
    this.bookingsPayload.post_id=this.post.id.toString();

    alert("Booking created successfully");  

    console.log("title boo"+ this.bookingsPayload.title + this.bookingsPayload.check_in_date);
  
    this.bookingsService.addBooking(this.bookingsPayload).subscribe(data => {
      alert("Succes creare Booking ");
      this.router_simple.navigateByUrl('/add-trip');
    }, error => {
      console.log('Failure Response');
    })
  
  }

  go(){

  
      if(!this.loginService.isAuthenticated())
      {
        this.login_invalid=true;
      }
      else{
        this.login_invalid=false;
        var myDivID = '../bookings/' + this.permaLink;
        window.location.href=myDivID ;   
        localStorage.setItem('id-booking', this.post.id.toString());
        this.addBookings();
      }
  
    }
    
    ngAfterContentChecked(){
 

      this.totalPrice = Math.round((this.price_post * this.qty_persons) * 100) / 100;
  
    
    }

}
