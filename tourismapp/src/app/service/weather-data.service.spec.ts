

import { WeatherDataService } from './weather-data.service';

import { TestBed } from '@angular/core/testing';



describe('WeatherDataService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: WeatherDataService = TestBed.get(WeatherDataService);
    expect(service).toBeTruthy();
  });
});
