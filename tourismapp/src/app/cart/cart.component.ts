import { Component, OnInit } from '@angular/core';
import { Observable} from 'rxjs';
import { LoginService } from '../service/login.service';
import { Router } from '@angular/router';
import { CartService } from '../service/cart.service';
import { CartPayload } from './cartPayload';
import {FormControl, FormGroup} from '@angular/forms';
import { PostPayload } from '../add-post/post-payload';
import { AddPostService } from '../service/add-post.service';
import { ActivatedRoute } from '@angular/router';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  cartForm: FormGroup;
  cartPayload: CartPayload;
  cartTotalPrice = new FormControl('');
  numberOfItems = new FormControl('');
 // posts:Observable<Array<PostPayload>>;
  posts:PostPayload[];
  name:string;
  postLength: number;
 
  
  carts:Observable<Array<CartPayload>>;
  constructor(private activatedRoute: ActivatedRoute, public loginService:LoginService, private cartService: CartService, private addPostService:AddPostService) {
    this.cartPayload = {
      cartTotalPrice:'',
      numberOfItems:'',
      username:'',
      post:''
    }

    

    this.name = this.loginService.getUserName();
    //this.name = this.activatedRoute.snapshot.params.name;
    this.addPostService.getAllPostsByUser(this.name).subscribe(data => {
      //this.posts = data;
      this.postLength = data.length;
    });

   }

   ngOnInit(): void {
    this.cartForm = new FormGroup({
      cartTotalPrice: new FormControl(''),
      numberOfItems: new FormControl(''),
      username: new FormControl(''),
      post: new FormControl('')
    });

   // this.posts = this.addPostService.getAllPosts();
  }

  addCart() {
    console.log("addCart funct");
  
    this.cartPayload.cartTotalPrice = this.cartForm.get('cartTotalPrice').value;
    this.cartPayload.numberOfItems = this.cartForm.get('numberOfItems').value;
    this.cartService.addCart(this.cartPayload).subscribe(data => {
      console.log("Succes creare cart");
  
    }, error => {
      throwError(error);
    })
  }
}
