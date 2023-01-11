import { ThisReceiver } from '@angular/compiler';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { BehaviorSubject } from 'rxjs';
import { HomeComponent } from '../home/home.component';
import { LocalStorageService } from 'ngx-webstorage';
@Injectable({
  providedIn: 'root'
})
export class ShareDataService {
  public searchTerm: BehaviorSubject<string> = new BehaviorSubject<string>(null);
}