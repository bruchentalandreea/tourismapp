import { Component, OnInit } from '@angular/core';
import { PostPayload } from '../add-post/post-payload';
import { AddPostService } from '../service/add-post.service';
import { Observable } from 'rxjs';
import { LoginService } from '../service/login.service';
import { Router } from '@angular/router';
import { FormControl, FormGroup } from '@angular/forms';
import { FeedbackPayload } from './feedback-payload';
import { FeedbackService } from '../service/feedback.service';
import { UserProfilePayload } from './user-profile-payload';
import { ThumbSettings } from '@syncfusion/ej2-charts';
import { AccountPageService } from '../service/account-page.service';
import { HttpClient } from '@angular/common/http';
import { LocalStorageService } from 'ngx-webstorage';
import { BookingsService } from '../service/bookings.service';
import { Alert } from 'selenium-webdriver';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

addFeedbackForm : FormGroup;
feedbackPayload: FeedbackPayload;

post_id= new FormControl('');
rating= new FormControl('');
user= new FormControl('');

currentIdPost : String; 
currentRating: String;
currentUsername: String;

phone_number_get:string;

  username:string ;
  postLength: number;
  posts:Observable<Array<PostPayload>>;
  bookingsLength: number;

  userProfileForm: FormGroup;
  userProfilePayload:UserProfilePayload;
  email = new FormControl(''); 
  user_role= new FormControl(''); 
  name= new FormControl(''); 
  surname= new FormControl(''); 
  phone_number= new FormControl(''); 
  country= new FormControl(''); 
  state= new FormControl(''); 
  city= new FormControl(''); 


  name_current : String;
  surname_current : String;
  phone_number_current : String;
  country_current : String;
  state_current : String; 
  city_current : String;


  userr:UserProfilePayload
  public show:boolean;
  public phone_invalid:boolean;
  public name_invalid:boolean;
  public surname_invalid:boolean;
  public invalid_location:boolean;
  public updated:boolean;
  public alert:boolean;

  alerts: Alert[];

  constructor(private bookingsService: BookingsService, private $localStorage: LocalStorageService, public loginService:LoginService, private router:Router, private addPostService: AddPostService, private feedbackService: FeedbackService, private accountPageService:AccountPageService,private httpClient: HttpClient) {
    this.username = this.loginService.getUserName();
    this.posts = this.addPostService.getAllPostsByUser(this.username);

    this.userProfileForm = new FormGroup({
      email:this.email,
      user_role: this.user_role,
      name: this.name,
      surname: this.surname,
      phone_number:this.phone_number,
      country:this.country,
      state:this.state,
      city:this.city
    });
    this.userProfilePayload = {
      email:'',
      user_role:'',
      name:'',
      surname:'',
      phone_number:'',
      country:'',
      state:'',
      city:''
    }

    this.userr = {
      email:'', 
      user_role:'',
      name:'',
      surname:'',
      phone_number:'',
      country:'',
      state:'',
      city:''
      
    }
  

  
    this.addPostService.getAllPostsByUser(this.username).subscribe(data => {
       this.postLength = data.length;
     });

     this.bookingsService.getBookingsByUsernamePost(this.username).subscribe(data => {
      this.bookingsLength = data.length;
    });

    this.bookingsService.getBookingsByUsernameHoliday(this.username).subscribe(data => {
      this.bookingsLength += data.length;
    });

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

    this.accountPageService.getUser(this.username).subscribe((data:UserProfilePayload) => {
      this.userr = data;
    },(err: any) => {
      console.log('Failure Response');
    })

    
    this.getValues();

  }



  addFeedback(){
    
    this.feedbackPayload.post_id=this.currentIdPost;
    this.feedbackPayload.rating=this.currentRating;
    this.feedbackPayload.username=this.currentUsername;
    console.log("CUrrent post ID"+ this.feedbackPayload.post_id);
    this.feedbackService.addFeedback(this.feedbackPayload).subscribe(data => {
      console.log("Succes creare post");
    }, error => {
      console.log('Failure Response');
    })


  }



  validateEmail(email) { 
    var EMAIL_REGEXP = /^([^@\s]+)@((?:[-a-z0-9]+\.)+[a-z]{2,})$/i;
    console.log(email)
      if ((email.length <= 5 || !EMAIL_REGEXP.test(email))) {
          console.log(EMAIL_REGEXP.test(email))
           alert("Email not valid");
      }

      return null;
}
  getValues() {


    this.userProfilePayload.name =this.userProfileForm.get('name').value;
    this.userProfilePayload.surname =this.userProfileForm.get('surname').value;
    this.userProfilePayload.phone_number =this.userProfileForm.get('phone_number').value;
    this.userProfilePayload.country =this.userProfileForm.get('country').value;
    this.userProfilePayload.state =this.userProfileForm.get('state').value;
    this.userProfilePayload.city=this.userProfileForm.get('city').value;

    localStorage.setItem('name_current', this.userProfilePayload.name.toString());
    localStorage.setItem('surname_current', this.userProfilePayload.surname.toString());
    localStorage.setItem('phone_number_current', this.userProfilePayload.phone_number.toString());
    localStorage.setItem('country_current', this.userProfilePayload.country.toString());
    localStorage.setItem('state_current', this.userProfilePayload.state.toString());
    localStorage.setItem('city_current', this.userProfilePayload.city.toString());
     
  }

submit(){
  this.userProfilePayload.name =this.userProfileForm.get('name').value;
  this.userProfilePayload.surname =this.userProfileForm.get('surname').value;
  this.userProfilePayload.phone_number =this.userProfileForm.get('phone_number').value;
  this.userProfilePayload.country =this.userProfileForm.get('country').value;
  this.userProfilePayload.state =this.userProfileForm.get('state').value;
  this.userProfilePayload.city=this.userProfileForm.get('city').value;
 if(localStorage.getItem('name_current') !=  this.userProfilePayload.name.toString())
 {
  this.updateName();
  console.log("Name changed");
 }
 if(localStorage.getItem('surname_current') !=  this.userProfilePayload.surname.toString() )
 {
  this.updateSurname();
  console.log("passed" + localStorage.getItem('surname_current') );
  console.log("current" +  this.userProfilePayload.surname.toString());
  console.log("surame changed");
 }
 if(localStorage.getItem('phone_number_current') !=  this.userProfilePayload.phone_number.toString() )
 {
  this.updatePhone();
  console.log("Phone changed");
 }

 if(localStorage.getItem('country_current') != this.userProfilePayload.country.toString() && localStorage.getItem('state_current') == this.userProfilePayload.state.toString() && localStorage.getItem('city_current')==   this.userProfilePayload.city.toString())
 {
  this.show =true;
  console.log("no changes");
 }

 if(localStorage.getItem('country_current') == this.userProfilePayload.country.toString() && localStorage.getItem('state_current') != this.userProfilePayload.state.toString() && localStorage.getItem('city_current')==   this.userProfilePayload.city.toString())
 {
  this.show =true;
  console.log("no changes");
 }

 if(localStorage.getItem('country_current') == this.userProfilePayload.country.toString() && localStorage.getItem('state_current') == this.userProfilePayload.state.toString() && localStorage.getItem('city_current')!=   this.userProfilePayload.city.toString())
 {
  this.show =true;
  console.log("no changes");
 }


 if(localStorage.getItem('country_current') != this.userProfilePayload.country.toString() && localStorage.getItem('state_current') != this.userProfilePayload.state.toString() && localStorage.getItem('city_current') !=   this.userProfilePayload.city.toString())
 {
  this.updateAddress();
  console.log("Address changed");
 }
 else{
 console.log("no changes");

 }
}



  updatePhone() {
    this.userProfilePayload.phone_number =this.userProfileForm.get('phone_number').value;
    if(  this.userProfilePayload.phone_number.match(/\d/g).length===10){
        this.accountPageService.updatePhone( this.userProfilePayload,this.userProfilePayload.phone_number,this.username).subscribe(data => {
          console.log("Succes update");
          this.alert=true;
          window.location.reload();
        }, error => {
          console.log('Failure Response');
        })
        this.phone_invalid=false;
      }
      else{
        this.phone_invalid=true;
      }
  }

  updateName() {
    this.userProfilePayload.name =this.userProfileForm.get('name').value;
    if(this.validate_names(this.userProfilePayload.name.toString())){
       this.name_invalid=false;
        this.accountPageService.updateName( this.userProfilePayload,this.userProfilePayload.name,this.username).subscribe(data => {
          console.log("Succes update");
          this.alert=true;
          window.location.reload();
        }, error => {
          console.log('Failure Response');
        })
      }
      else{
        this.name_invalid=true;
      }
  }

  updateSurname() {
    this.userProfilePayload.surname=this.userProfileForm.get('surname').value;
    if(this.validate_names(this.userProfilePayload.surname.toString())){
        this.accountPageService.updateSurname( this.userProfilePayload,this.userProfilePayload.surname,this.username).subscribe(data => {
          console.log("Succes update");
          this.alert=true;
          window.location.reload();
        }, error => {
          console.log('Failure Response');
        })
        this.surname_invalid=false;
      }
      else{
        this.surname_invalid=true;
      }
  }

  updateAddress() {
    this.userProfilePayload.country =this.userProfileForm.get('country').value;
    this.userProfilePayload.state =this.userProfileForm.get('state').value;
    this.userProfilePayload.city=this.userProfileForm.get('city').value;
    if(this.validate_names(this.userProfilePayload.country)&& this.validate_names(this.userProfilePayload.state) && this.validate_names(this.userProfilePayload.city)){
        this.accountPageService.updateAddress( this.userProfilePayload,this.userProfilePayload.country,this.userProfilePayload.state,this.userProfilePayload.city,this.username).subscribe(data => {
          console.log("Succes update");
          this.alert=true;
          window.location.reload();
        }, error => {
          console.log('Failure Response');
        })
        this.invalid_location=false;
      }
      else{
        this.invalid_location=true;
      }
  }

  validate_names(name) {
    var regex = /^[a-zA-Z ]{2,30}$/;
    return regex.test(name);
}

update_valid(){
  this.updated = true;
}

close(alert: Alert) {
  this.alerts.splice(this.alerts.indexOf(alert), 1);
}

}
