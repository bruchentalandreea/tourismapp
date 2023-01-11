import { Component, OnInit } from '@angular/core';
import { CategoryPayload } from '../add-trip/category-payload';
import { Observable } from 'rxjs';
import { AddPostService } from '../service/add-post.service';
import { FormControl, FormGroup } from '@angular/forms';
import { WalletPayload } from './wallet-payload';
import { WalletService } from '../service/wallet.service';
import { LoginService } from '../service/login.service';
import { NumericLiteral } from 'typescript';
import {Cashify} from 'cashify';

@Component({
  selector: 'app-wallet',
  templateUrl: './wallet.component.html',
  styleUrls: ['./wallet.component.css']
})
export class WalletComponent implements OnInit {
  categories: Observable<Array<CategoryPayload>>;


  wallet: WalletPayload;
  username:String;
  
  value:number;
  currency_from:String;
  currency_to:String;
  USD_buy:number=4.0650;
  USD_sell:number =4.2600;
  EUR_buy:number=4.8700;
  EUR_sell:number=4.9800;
  GBP_buy:number=5.5550;
  GBP_sell:number=5.8500;
  result:number=0;
  type:string;

  new_amount:number;
  value_new:number;
  constructor(public addPostService: AddPostService,private walletService:WalletService,private loginService:LoginService) { 

  
    this.wallet =
    {
    id:'',
    username:'',
    amount:0
    }
  }

  ngOnInit(): void {
    this.categories=this.addPostService.getAllCategories();
    this.username = this.loginService.getUserName();
  

    this.walletService.getWallet(this.username).subscribe((data:WalletPayload) => {
      this.wallet = data;
      console.log("wallet"+ this.wallet.amount);
    },(err: any) => {
      console.log('Failure Response');
    })

  }


  convert(){
    if(this.currency_from == "EUR" && this.currency_to=="USD")
    {
        this.result = this.value * 1.1459219;
    }

    if(this.currency_from == "USD" && this.currency_to=="EUR")
    {
        this.result = this.value * 0.87273005;
    }

    if(this.currency_from == "EUR" && this.currency_to=="GBP")
    {
        this.result = this.value * 0.84661936;
    }

    if(this.currency_from == "GBP" && this.currency_to=="EUR")
    {
        this.result = this.value * 1.1811684 ;
    }

    if(this.currency_from == "USD" && this.currency_to=="GBP")
    {
        this.result = this.value * 0.73879025 ;
    }

    if(this.currency_from == "GBP" && this.currency_to=="USD")
    {
        this.result = this.value * 1.3535642  ;
    }
  }

  update_amount(){
    
    if(this.type =="Income")
    {
      this.new_amount = + this.wallet.amount ;
      this.new_amount = this.new_amount + this.value_new;
      this.walletService.updateWallet( this.wallet,this.new_amount,this.username).subscribe(data => {
        window.location.reload();
      }, error => {
        console.log('Failure Response');
      })
    }
    
    if(this.type =="Expense")
    {
      this.new_amount = + this.wallet.amount ;
      this.new_amount = this.new_amount - this.value_new;
      this.walletService.updateWallet( this.wallet,this.new_amount,this.username).subscribe(data => {
        window.location.reload();
      }, error => {
        console.log('Failure Response');
      })
    }
  }
 
}
