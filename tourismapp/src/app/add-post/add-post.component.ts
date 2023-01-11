import { Component, OnInit } from '@angular/core';
import 'tinymce/icons/default';
import { FileUploadService } from '../service/file-upload.service';
import {FormControl, FormGroup} from '@angular/forms';
import {PostPayload} from './post-payload';
import {Router} from '@angular/router';
import { AddPostService } from '../service/add-post.service';
import { HttpClient, HttpEventType } from '@angular/common/http';
import { Timestamp } from 'rxjs/internal/operators/timestamp';

@Component({
  selector: 'app-add-post',
  templateUrl: './add-post.component.html',
  styleUrls: ['./add-post.component.css']
})
export class AddPostComponent implements OnInit {
  //image
  selectedFile: File;
  retrievedImage: any;
  base64Data: any;
  retrieveResonse: any;
  message: string;
  imageName: any;
//




  fileUploadService: FileUploadService;
  addPostForm: FormGroup;
  postPayload: PostPayload;
  title = new FormControl('');
  body = new FormControl('');
  price = new FormControl('');
  check_in_date = new FormControl('');
  check_out_date = new FormControl('');
  country = new FormControl('');
  state = new FormControl('');
  city = new FormControl('');
  photo = new FormControl(''); 
  type_of_vacation = new FormControl('');
  

  type: any = ['City break','Summer vacation', 'Winter vacation', 'Business vacation', 'Honeymoon', 'Cruise'];

  constructor(private httpClient: HttpClient, private addpostService: AddPostService, private router: Router) {
    this.addPostForm = new FormGroup({
      title: this.title,
      body: this.body,
      price:this.price,
      check_in_date:this.check_in_date,
      check_out_date:this.check_out_date,
      country:this.country,
      state:this.state,
      city:this.city,
      type_of_vacation:this.type_of_vacation

    });
    this.postPayload = {
      id: '',
      content: '',
      title: '',
      username: '',
      price: 0 ,
      created_on:'',
      updated_on:'',
      check_in_date:'',
      check_out_date:'',
      country:'',
      state:'',
      city:'',
      type_of_vacation:''
    }
  }

  ngOnInit(): void {
  }
 
  
addPost() {

 
  var d1 = new Date(this.addPostForm.get('check_in_date').value ).toLocaleDateString("en-US")
  var d2 = new Date(this.addPostForm.get('check_out_date').value ).toLocaleDateString("en-US")


  
  this.postPayload.content = this.addPostForm.get('body').value;
  this.postPayload.title = this.addPostForm.get('title').value;
  this.postPayload.price = this.addPostForm.get('price').value;
  this.postPayload.check_in_date= this.addPostForm.get('check_in_date').value;
  this.postPayload.check_out_date=this.addPostForm.get('check_out_date').value;
  this.postPayload.country = this.addPostForm.get('country').value;
  this.postPayload.state = this.addPostForm.get('state').value;
  this.postPayload.city = this.addPostForm.get('city').value;
  this.postPayload.type_of_vacation = this.addPostForm.get('type_of_vacation').value;

  alert("Post created successfully");

  this.addpostService.addPost(this.postPayload).subscribe(data => {
    console.log("Succes creare post");
    this.router.navigateByUrl('/add-trip');
  }, error => {
    console.log('Failure Response');
  })
}




}
