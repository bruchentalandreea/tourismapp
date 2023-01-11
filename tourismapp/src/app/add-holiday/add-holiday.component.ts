import { Component, OnInit } from '@angular/core';
import 'tinymce/icons/default';
import { FileUploadService } from '../service/file-upload.service';
import {FormControl, FormGroup} from '@angular/forms';
import { HolidayPayload } from './holiday-payload';
import {Router} from '@angular/router';
import { HttpClient, HttpEventType } from '@angular/common/http';
import { Timestamp } from 'rxjs/internal/operators/timestamp';
import { HolidayService } from '../service/holiday.service';



@Component({
  selector: 'app-add-holiday',
  templateUrl: './add-holiday.component.html',
  styleUrls: ['./add-holiday.component.css']
})
export class AddHolidayComponent implements OnInit {
 //image
 selectedFile: File;
 retrievedImage: any;
 base64Data: any;
 retrieveResonse: any;
 message: string;
 imageName: any;
//




 fileUploadService: FileUploadService;
 addHolidayForm: FormGroup;
 holidayPayload: HolidayPayload;
 title = new FormControl('');
 body = new FormControl('');
 price = new FormControl('');
 country = new FormControl('');
 state = new FormControl('');
 city = new FormControl('');
 photo = new FormControl(''); 
 nr_of_persons =  new FormControl('');
 type_of_vacation = new FormControl('');


 type: any = ['City break','Summer vacation', 'Winter vacation', 'Business vacation', 'Honeymoon', 'Cruise'];

 constructor(private httpClient: HttpClient, private holidayService: HolidayService, private router: Router) {
   this.addHolidayForm = new FormGroup({
     title: this.title,
     body: this.body,
     price:this.price,
     country:this.country,
     state:this.state,
     city:this.city,
     nr_of_persons:this.nr_of_persons,
     type_of_vacation:this.type_of_vacation
   });
   this.holidayPayload = {
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
 }



 ngOnInit(): void {
 }

 fileToUpload: File | null = null;
 handleFileInput(files: FileList) {
   this.fileToUpload = files.item(0);
}

uploadFileToActivity() {
 this.fileUploadService.postFile2(this.fileToUpload).subscribe(data => {
   // do something, if upload success
   }, error => {
     console.log(error);
   });
}


 //Gets called when the user selects an image
 public onFileChanged(event) {
   //Select File
   this.selectedFile = event.target.files[0];
 }
 //Gets called when the user clicks on submit to upload the image
 onUpload() {
   console.log(this.selectedFile);
   
   //FormData API provides methods and properties to allow us easily prepare form data to be sent with Holiday HTTP requests.
   const uploadImageData = new FormData();
   uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);
 
   //Make a call to the Spring Boot Application to save the image
   this.httpClient.post('http://localhost:8080/api/image/upload', uploadImageData, { observe: 'response' })
     .subscribe((response) => {
       if (response.status === 200) {
         this.message = 'Image uploaded successfully';
       } else {
         this.message = 'Image not uploaded successfully';
       }
     }
     );
 }
   //Gets called when the user clicks on retieve image button to get the image from back end
   getImage() {
   //Make a call to Sprinf Boot to get the Image Bytes.
   this.httpClient.get('http://localhost:8080/api/image/get/' + this.imageName)
     .subscribe(
       res => {
         this.retrieveResonse = res;
         this.base64Data = this.retrieveResonse.picByte;
         this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;
       }
     );
 }



addHoliday() {
 console.log(this.title);
 console.log(this.body);
 console.log(this.price);

 
 this.holidayPayload.content = this.addHolidayForm.get('body').value;
 this.holidayPayload.title = this.addHolidayForm.get('title').value;
 this.holidayPayload.price = this.addHolidayForm.get('price').value;
 this.holidayPayload.country = this.addHolidayForm.get('country').value;
 this.holidayPayload.state = this.addHolidayForm.get('state').value;
 this.holidayPayload.city = this.addHolidayForm.get('city').value;
 this.holidayPayload.type_of_vacation = this.addHolidayForm.get('type_of_vacation').value;

 this.holidayService.addHoliday(this.holidayPayload).subscribe(data => {
   console.log("Succes creare Holiday");
   this.router.navigateByUrl('/add-trip');
 }, error => {
   console.log('Failure Response');
 })
}


changeType(e) {
 this.type_of_vacation.setValue(e.target.value, {
   onlySelf: true
 })
}

}
