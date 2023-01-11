import { AfterViewChecked, AfterViewInit, Component, OnInit, Output, ViewChildren } from '@angular/core';
import { HomeComponent} from '../home/home.component';
import { Observable } from 'rxjs';
import { TripPayload } from '../add-trip/trip-payload';
import { AddPostService } from '../service/add-post.service';
import { EventEmitter } from '@angular/core';
import { ViewChild } from '@angular/core';
import { ShareDataService } from '../service/share-data.service';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-search-results',
  
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.css'],
})
export class SearchResultsComponent {
  posts: Observable<Array<TripPayload>>;

  public city:string;
  nr_of_persons:number;
  type_of_vacation:string;
  city_search:string;
  searchTerm='';

  empty$: Observable<boolean>;

  constructor(private cdRef:ChangeDetectorRef,private sharedDataService: ShareDataService,public home:HomeComponent, private addPostService:AddPostService) { 

  
  }

  ngOnInit(){
    // this listens to the input value from the service and does something on change.
    this.sharedDataService.searchTerm.subscribe((newValue: string) => {
      // this is where you would apply your existing filtering.
         this.searchTerm = newValue;
       });
       this.city_search=localStorage.getItem('city_search');
       this.nr_of_persons=Number(localStorage.getItem('nr_of_persons'));
       this.type_of_vacation=localStorage.getItem('type_of_vacation');
       console.log(localStorage.getItem('city_search'));
       console.log(Number(localStorage.getItem('nr_of_persons')));
       console.log(localStorage.getItem('type_of_vacation'));
      
       this.search();
  }

  search(){
    this.posts = this.addPostService.searchPost(this.nr_of_persons,this.city_search,this.type_of_vacation);
  
  }


  public go(city_search,nr_of_persons,type_of_vacation){
    window.location.href='../search-results';
    localStorage.setItem('city_search', city_search);
    localStorage.setItem('nr_of_persons', nr_of_persons);
    localStorage.setItem('type_of_vacation', type_of_vacation);
  }
}
