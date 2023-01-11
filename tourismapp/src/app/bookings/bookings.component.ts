import { Component, OnInit,  ChangeDetectionStrategy  } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { BookingsPayload } from './bookings-payload';
import { BookingsService } from '../service/bookings.service';
import { FormControl } from '@angular/forms';
import { ActivatedRoute} from '@angular/router';
import { Validators } from '@angular/forms';
import { Subject } from 'rxjs';
import { BehaviorSubject } from 'rxjs';
import { ValidatorFn } from '@angular/forms';
import { AbstractControl } from '@angular/forms';
import { FormBuilder} from '@angular/forms';



@Component({
  selector: 'app-bookings',
  templateUrl: './bookings.component.html',
  styleUrls: ['./bookings.component.css']
})
export class BookingsComponent implements OnInit {
  bookings: BookingsPayload;
  id_booking:Number;
  card_show:boolean =false ;
  type_pay= new FormControl('none');
  type: string;
  type_pay_new:string;
  photo:String;


  form: FormGroup;
  submitted: boolean = false;
  creditCard:string ='';
  expirationDate :string ='';
  cvv :string ='';

  card_invalid:boolean;
  cvv_invalid:boolean;
  expiration_date_invalid:boolean;
  cash_button:boolean = false;
  constructor( private _fb: FormBuilder, private bookingsService :BookingsService, public routerActivated: ActivatedRoute) {
    this.bookings = {
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

   }

  ngOnInit(): void {
    

   


    this.id_booking= + localStorage.getItem('id-booking');
    


    this.bookingsService.getBookingLast().subscribe((data:BookingsPayload) => {
      this.bookings = data;
    },(err: any) => {
      console.log('Failure Response');
    })


   

   
     
  }



  ngAfterViewInit():void{

    console.log(this.type + "TYPE")
    if(this.type === "Credit card")
    {
      this.card_show=true;
  
    }
    else{
      this.card_show=false;
     
    }
  }

  card_nr_validation():boolean{
    var visaRegEx =/^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\d{3})\d{11})$/;
    if(!visaRegEx.test(this.creditCard))
    {
      this.card_invalid=true;
      return false;
    }
    else{
      return true;
    }
  }
  cvv_validation():boolean{
    var cvvRegEx= /^[0-9]{3,4}$/;
    if(!cvvRegEx.test(this.cvv)){
      this.cvv_invalid=true;
      return false;
    }
  
  else{
    return true;
  }
  }

  expiration_validation():boolean{
    var expRegEx = /^(0[1-9]|1[0-2])\/?([0-9]{4}|[0-9]{2})$/;
    if(!expRegEx.test(this.expirationDate))
    {
      this.expiration_date_invalid=true;
      return false;
    }
  
  else{
    return true;
  }
  }



   onSubmit() {

     if(this.card_nr_validation()==true && this.expiration_validation()==true && this.cvv_validation()==true){
      alert("Thank you for the purchase");
      window.location.href="/holidays";
     }
  }


  pay(type){
    if(type === "Credit card")
    {
      this.card_show=true;
  
    }
    else{
      this.card_show=false;
      this.cash_button=true;
     
    }
  }

  go(){
    alert("Thank you for the purchase");
    window.location.href="/holidays";
  }
}
