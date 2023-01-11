import { Component, OnInit,ViewChild } from '@angular/core';
import * as _moment from 'moment';
// tslint:disable-next-line:no-duplicate-imports
import {default as _rollupMoment, Moment} from 'moment';
import { ChartType, ChartOptions, ChartDataSets } from 'chart.js';
import { SingleDataSet, Label, monkeyPatchChartJsLegend, monkeyPatchChartJsTooltip } from 'ng2-charts';
import { HolidayPayload } from '../add-holiday/holiday-payload';
import { HolidayService } from '../service/holiday.service';
import { Observable } from 'rxjs';
import { Color } from 'ng2-charts';
import { Border } from '@syncfusion/ej2-angular-charts';
import { GraphicPayload } from './graphicPayload';
import { LoginService } from '../service/login.service';
import { TripPayload } from '../add-trip/trip-payload';
import { AddPostService } from '../service/add-post.service';



@Component({
  selector: 'app-line-chart',
  templateUrl: './line-chart.component.html',
  styleUrls: ['./line-chart.component.css'],

})
export class LineChartComponent implements OnInit {
 

////////chart1
  barChartOptions: ChartOptions = {
    responsive: true,
    aspectRatio:5,
    scales: {
      yAxes: [{id: 'y-axis-1', type: 'linear', position: 'left', ticks: {min: 0, max:10}}]
    }
  };
  barChartLabels: Label[] = ['Holidays', 'Offers', 'Recommendations'];
  barChartType: ChartType = 'bar';
  barChartLegend = false;
  barChartPlugins = [];
  colors:Color[] =[
    { 
      backgroundColor:["#4eb478", "#1A466882", "#009933"] 
    }];

  public chartReady: boolean = false ;
  barChartData1:GraphicPayload[];

////////chart2
barChartOptions2: ChartOptions = {
  responsive: true,
  aspectRatio:5,

};
barChartLabels2: Label[] = ['Romania', 'Others'];
barChartType2: ChartType = 'doughnut';
barChartLegend2 = false;

colors2:Color[] =[
  { 
    backgroundColor:["#4eb478", "#1A466882", "#009933"] 
  }];

public chartReady2: boolean = false ;
barChartData2:GraphicPayload[];

  currentUsername:String;
  total_holidays:number;
  username:string;
  posts: Observable<Array<TripPayload>>;
  constructor(private holidayService:HolidayService, private loginService:LoginService, private addPostService:AddPostService) {
    monkeyPatchChartJsTooltip();
    monkeyPatchChartJsLegend();
   }

  ngOnInit() {
    this.currentUsername = this.loginService.getUserName();
    this.username = this.loginService.getUserName();
    this.posts =this.addPostService.getRecommendations(this.username);
    this.barChart();
    this.barChart2();
   
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

  barChart2(){
    this.holidayService.getNrOfHolidaysByCountry("Romania").subscribe(data => {
      this.barChartData2=data;
      
      this.chartReady2 = true;
      console.log(this.barChartData2 + "HELLO 2")
      

    },(err: any) => {
      console.log('Failure Response');
    })
  }
}
