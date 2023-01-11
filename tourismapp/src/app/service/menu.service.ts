import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MenuService {
  public darkMode: boolean | undefined;

  /**
   * Enable/disable "dark mode" by flipping the bit.
   */
  public toggle(): void {

      this.darkMode = !this.darkMode;
      console.log("Service Home!!!");

  }

  constructor() { }
}
