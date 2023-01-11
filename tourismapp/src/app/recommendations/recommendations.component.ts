import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { TripPayload } from '../add-trip/trip-payload';
import { AddPostService } from '../service/add-post.service';
import { LoginService } from '../service/login.service';
import { GraphicPayload } from '../line-chart/graphicPayload';
import { ChartType, ChartOptions, ChartDataSets } from 'chart.js';
import { Color,Label } from 'ng2-charts';
import { HolidayService } from '../service/holiday.service';
import { HolidayPayload } from '../add-holiday/holiday-payload';

@Component({
  selector: 'app-recommendations',
  templateUrl: './recommendations.component.html',
  styleUrls: ['./recommendations.component.css']
})
export class RecommendationsComponent implements OnInit {


  barChartOptions: ChartOptions = {
    responsive: true,
    aspectRatio:2,
    scales: {
      yAxes: [{id: 'y-axis-1', type: 'linear', position: 'left', ticks: {min: 0, max:10}}]
    }
  };
  barChartLabels: Label[] = ['Holidays', 'Offers', 'Recommended Offers','Recommended Holidays'];
  barChartType: ChartType = 'bar';
  barChartLegend = false;
  barChartPlugins = [];
 
  colors:Color[] =[
    { 
      backgroundColor:["#4eb478", "#1A466882", "#009933"] 
    }];

  public chartReady: boolean = false ;
  barChartData1:GraphicPayload[];
  currentUsername:String;


  total_holidays:number;
  username:string;
  posts: Observable<Array<TripPayload>>;
  holidays:Observable<Array<HolidayPayload>>;
  constructor( private addPostService: AddPostService, private loginService: LoginService, private holidayService:HolidayService) { }

  ngOnInit(): void {
    
    this.username = this.loginService.getUserName();
    this.posts =this.addPostService.getRecommendations(this.username);
    this.holidays= this.holidayService.getRecommendations(this.username);
    this.currentUsername = this.loginService.getUserName();
    this.barChart();
 
  }
  barChart(){
    this.holidayService.getNrOfHolidays(this.currentUsername).subscribe(data => {
      this.barChartData1=data;
      
      this.chartReady = true;
      console.log(this.barChartData1 + "HELLO")
      

    },(err: any) => {
      console.log('Failure Response');
    })


  }
}
