
import { Component, OnInit, ViewChild } from '@angular/core';
import { MenuService} from './service/menu.service'
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginService } from './service/login.service';
import {MatToolbarModule} from '@angular/material/toolbar';
import { AddPostService } from './service/add-post.service';
import { PostPayload } from './add-post/post-payload';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{


  title(title: any) {
    throw new Error("Method not implemented.");
    
  }
  posts: Array<PostPayload> = [];

constructor(private addPostService:AddPostService, private router: Router,public menuService: MenuService,public loginService:LoginService){
  this.addPostService.getAllPosts().subscribe(post => {
    this.posts = post;
  });
}
ngOnInit() {}
onClickGenerateHome(){
  this.router.navigate(['/home'])
}

logout(){
  this.loginService.logout();
  console.log("logout");
}

}


