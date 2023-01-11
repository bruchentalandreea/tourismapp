import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { LoginService } from '../service/login.service';
import { LoginPayload } from './login-payload';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  loginForm: FormGroup;
  loginPayload: LoginPayload;
  public loading: boolean;
  public login_fail:boolean;

  constructor(private loginService:LoginService, private router: Router) {
    this.loginForm=new FormGroup({
    username : new FormControl(),
    password : new FormControl()
  });
    this.loginPayload = {
      username:'',
      password:''
    };
  }

  ngOnInit(): void {
  }

  onSubmit (){
    this.loginPayload.username=this.loginForm.get('username').value;
    this.loginPayload.password= this.loginForm.get('password').value;
    if( !this.loginPayload.username)
    {
      this.login_fail=true;
    }
    if( ! this.loginPayload.password)
    {
      this.login_fail=true;
    }
    if( this.loginPayload.username,  this.loginPayload.password)
    {
    this.loading = true;
    console.log(this.loginPayload.username);
    console.log(this.loginPayload.password);

    this.loginService.login(this.loginPayload).subscribe(data =>{
      console.log('login success');
      this.login_fail=false;
      this.router.navigateByUrl('/home');
    }, error => {
    console.log('fail');
    this.login_fail=true;
    this.loading=false;
    });
  }
  }


 
}

