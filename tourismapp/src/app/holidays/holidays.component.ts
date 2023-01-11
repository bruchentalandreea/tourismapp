import { Component, OnInit } from '@angular/core';
import { BookingsService } from '../service/bookings.service';
import { BookingsPayload } from '../bookings/bookings-payload';  
import { LoginService } from '../service/login.service';
import { Observable } from 'rxjs';
import { withIn } from '@syncfusion/ej2-angular-charts';


@Component({
  selector: 'app-holidays',
  templateUrl: './holidays.component.html',
  styleUrls: ['./holidays.component.css']
})
export class HolidaysComponent implements OnInit {

  bookings_post: Observable<Array<BookingsPayload>>; 
  bookings_holiday: Observable<Array<BookingsPayload>>; 
  currentUsername:String;
  bookings_length:number;
  public no_trips_post:boolean;
  public no_trips_holiday:boolean;
  

  constructor(private bookingsService :BookingsService, private loginService:LoginService) {
   }

  ngOnInit(): void {
    
    this.currentUsername = this.loginService.getUserName();


  this.bookings_post=  this.bookingsService.getBookingsByUsernamePost(this.currentUsername)

  this.bookingsService.getBookingsByUsernamePost(this.currentUsername).subscribe(data => {
    this.bookings_length = data.length;
    if(this.bookings_length <= 0)
    {
      this.no_trips_post=true;
    }
  });

  this.bookings_holiday=  this.bookingsService.getBookingsByUsernameHoliday(this.currentUsername)

  this.bookingsService.getBookingsByUsernameHoliday(this.currentUsername).subscribe(data => {
    this.bookings_length = data.length;
    if(this.bookings_length <= 0)
    {
      this.no_trips_holiday=true;
    }
  });
  }

cancel_post(id)
{
 this.bookings_post =  this.bookingsService.deleteBooking(id);
  window.location.reload();
  console.log("Deleted Booking" + id );
}

cancel_holiday(id)
{
 this.bookings_holiday =  this.bookingsService.deleteBooking(id);
  window.location.reload();
  console.log("Deleted Booking" + id );
}


go_review_post(id,booking_id)
{
  localStorage.setItem('show_stars',"show");
  this.bookings_post =  this.bookingsService.deleteBooking(booking_id);
  var myDivID = '../single-post/' + id;
  window.location.href=myDivID ;   
}

go_review_holiday(id,booking_id)
{
  localStorage.setItem("show_stars","show");
  this.bookings_holiday =  this.bookingsService.deleteBooking(booking_id);
  var myDivID = '../single-holiday/' + id;
  window.location.href=myDivID ;   
}
}
