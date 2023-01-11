import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CalendarService {
  public darkMode: boolean | undefined;

  public toggle(): void {

      this.darkMode = !this.darkMode;
      console.log("Service Home!!!");

  }
  constructor() { }
}