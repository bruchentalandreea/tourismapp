import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { FormGroup } from '@angular/forms';
import { LoginService } from '../service/login.service';
@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {
  show = false;
  mail : "";
  name = new FormControl('');
  registerForm: FormGroup;
  email= new FormControl('');

  constructor(private loginService:LoginService) {
    this.registerForm = new FormGroup({
      name:this.name,
      email:this.email
    })
   }

  ngOnInit(): void {
  }
  sendEmail() {
    const data = {
      toEmail: 'andreeabruchental@gmail.com',
      toName: 'Jeff Delaney'
    }}

    validateEmail(email) {
      var EMAIL_REGEXP = /^([^@\s]+)@((?:[-a-z0-9]+\.)+[a-z]{2,})$/i;
      console.log(this.mail)
        if ((this.mail.length <= 5 || !EMAIL_REGEXP.test(this.mail))) {
            console.log(EMAIL_REGEXP.test(this.mail))
             alert("Email not valid");
        }

        return null;
  }

    onSubmit() {
      this.mail = this.registerForm.get('email').value;
      if ( this.mail == "") {
        alert("Enter an email");
        return false;
      }
      else{
        this.validateEmail(this.mail);
      }
      console.log(this.mail)
      this.loginService.addEmail(this.mail).subscribe(data=>{
        console.log(this.mail)
        alert("Email sent!")
        }
       );
    }
 
}
