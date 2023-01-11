import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Store, select } from '@ngrx/store';
import { WeatherDataService } from 'src/app/service/weather-data.service';

@Component({
  selector: 'app-current-weather',
  templateUrl: './current-weather.component.html',
  styleUrls: ['./current-weather.component.css']
})
export class CurrentWeatherComponent implements OnInit {

  loc$: Observable<string>;
  loc: string;
  currentWeather: any = <any>{};
  msg: string;

  constructor(
    private store: Store<any>,
    private weatherService: WeatherDataService
  ) {
    this.loc$ = store.pipe(select('loc'));
    this.loc$.subscribe(loc => {
      this.loc = loc;
      this.searchWeather(loc);
    })
  }

  ngOnInit() {
  }

  searchWeather(loc: string) {
    this.msg = '';
    this.currentWeather = {};
    this.weatherService.getCurrentWeather(loc)
      .subscribe(res => {
        this.currentWeather = res;
      }, err => {
        if (err.error && err.error.message) {
          alert(err.error.message);
          console.error('An error occurred:', err.error);
          this.msg = err.error.message;
          return;
        }
        alert('Failed to get weather.');
        console.error('An error occurred:', err.error);
      }, () => {

      })
  }

  resultFound() {
    return Object.keys(this.currentWeather).length > 0;
  }
}
