import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {FormPayload} from './form-payload';
import { AccountPageService } from '../service/account-page.service';
import {Router} from '@angular/router';
import { WalletPayload } from '../wallet/wallet-payload';
import { WalletService } from '../service/wallet.service';

@Component({
  selector: 'app-account-page',
  templateUrl: './account-page.component.html',
  styleUrls: ['./account-page.component.css']
})
export class AccountPageComponent implements OnInit {
  registerForm: FormGroup;
  accountPage: AccountPageComponent;
  formPayload: FormPayload;
  public loading: boolean;
  public selectedType: string;
  role: any = ['company','tourist'];

  public invalid_email:boolean;
  public username_invalid:boolean;
  public password_match:boolean;
  confirm_password= new FormControl('');

////////wallet
  walletForm: FormGroup;
  walletPayload:WalletPayload;
  id= new FormControl('');
  username= new FormControl('');
  amount= new FormControl('');

  constructor(private walletService:WalletService, private formBuilder: FormBuilder, private accountPageService: AccountPageService, private router: Router) {
    this.registerForm=this.formBuilder.group( {
      username: '',
      email: '',
      password: '',
      user_role: '',
    confirm_password:''});
    this.formPayload={
      username:'',
      email:'',
      password :'',
      user_role:''}

      this.walletForm= new FormGroup({
        id:this.id,
        username:this.username,
        amount:this.amount
      });
  
      this.walletPayload =
      {
      id:'',
      username:'',
      amount:0
  
      }
  
   }

  ngOnInit(): void {
  }
  onLogin() {


    this.formPayload.username=this.registerForm.get('username').value;
    this.formPayload.email=this.registerForm.get('email').value;
    this.formPayload.password=this.registerForm.get('password').value;
    this.formPayload.user_role=this.registerForm.get('user_role').value;
 
   
      if (  this.formPayload.password == "") {
        alert("Enter a password");
        return false;
      }
      if( this.formPayload.password !=  this.registerForm.get('confirm_password').value)
      {
        this.password_match=true;
      }

      if(!this.email_validation( this.formPayload.email))
      {
        this.invalid_email=true;
        this.loading=false;
      }

      if(!this.username_validation(this.formPayload.username))
      {
        this.username_invalid=true;
      }

      if(this.username_validation(this.formPayload.username) && this.email_validation( this.formPayload.email) && this.formPayload.password ==  this.registerForm.get('confirm_password').value){
        this.invalid_email=false;
        this.accountPageService.register(this.formPayload).subscribe(data =>{
            console.log('login success');
            this.loading=true;
            this.create_wallet(this.formPayload.username);
            alert("Inregistrarea a avut succes");
            this.router.navigateByUrl('/login-page');
            
        }, error => {
        console.log('fail');
        });
    }

    console.log(this.formPayload.username);
    console.log(this.formPayload.email);
    console.log(this.formPayload.password);
    console.log(this.formPayload.user_role);
   
   

  
}

email_validation(email){
  var regex = new RegExp(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/);
  return regex.test(email);
}

username_validation(username){
    var regex = /^[a-zA-Z ]{2,30}$/;
    return regex.test(username);
}

create_wallet(user){
  this.walletPayload.username=user;
  this.walletPayload.amount=0;
  this.walletService.addWallet(this.walletPayload).subscribe(data =>{
    console.log('login success');
    this.loading=true;
    alert("Creare wallet");

    
}, error => {
console.log('fail');
});
}
}

