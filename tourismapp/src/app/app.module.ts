import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatIconModule} from '@angular/material/icon';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatMenuModule} from '@angular/material/menu';
import { AccountPageComponent } from './account-page/account-page.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { HomeComponent } from './home/home.component';
import { RouterModule } from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { LoginPageComponent } from './login-page/login-page.component';
import { AddTripComponent } from './add-trip/add-trip.component';
import {MatCheckboxModule} from '@angular/material/checkbox';
import { WalletComponent } from './wallet/wallet.component';
import { EventComponent } from './event/event.component';
import { WeatherComponent } from './weather/weather.component';
import { CartComponent } from './cart/cart.component';
import { LineChartComponent } from './line-chart/line-chart.component';
import {NgxWebstorageModule} from 'ngx-webstorage';
import { EditorModule } from '@tinymce/tinymce-angular';
import { AddPostComponent } from './add-post/add-post.component';
import { HttpClientInterceptor } from './service/http-client-interceptor';
import { SinglePostComponent } from './single-post/single-post.component';
import { AuthGuard } from './service/auth.guard';
import { DisplayPostComponent } from './display-post/display-post.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import { ContactComponent } from './contact/contact.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { UserProfileComponent } from './user-profile/user-profile.component';
import {MatGridListModule} from '@angular/material/grid-list';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { NgbCarouselModule } from '@ng-bootstrap/ng-bootstrap';
import { CarouselModule, WavesModule } from 'angular-bootstrap-md'
import { BubbleSeriesService, CategoryService, LineSeriesService, BarSeriesService} from '@syncfusion/ej2-angular-charts';
import { SearchResultsComponent } from './search-results/search-results.component';
import { ShareDataService } from './service/share-data.service';
import { WeatherDataService } from './service/weather-data.service';
import { UvComponent } from './weather/uv/uv.component';
import { CurrentWeatherComponent } from './weather/current-weather/current-weather.component';
import { ForecastComponent } from './weather/forecast/forecast.component';
import { StoreModule } from '@ngrx/store';
import { locationReducer } from './weather/reducer-weather';
import { TopBarComponent } from './weather/top-bar/top-bar.component';
import { AddHolidayComponent } from './add-holiday/add-holiday.component';
import { SingleHolidayComponent } from './single-holiday/single-holiday.component';
// MDB Angular Pro
import { ButtonsModule, CollapseModule } from 'angular-bootstrap-md';
import { RecommendationsComponent } from './recommendations/recommendations.component';
import { BookingsComponent } from './bookings/bookings.component';
import { HolidaysComponent } from './holidays/holidays.component'
import { ChartsModule } from 'ng2-charts';
import { CreditCardDirectivesModule } from 'angular-cc-library';
import {MatTableModule} from '@angular/material/table';


@NgModule({
  declarations: [
    AppComponent,
    AccountPageComponent,
    HomeComponent,
    LoginPageComponent,
    AddTripComponent,
    WalletComponent,
    EventComponent,
    WeatherComponent,
    CartComponent,
    LineChartComponent,
    AddPostComponent,
    SinglePostComponent,
    DisplayPostComponent,
    ContactComponent,
    UserProfileComponent,
    SearchResultsComponent,
    UvComponent,
    CurrentWeatherComponent,
    ForecastComponent,
    TopBarComponent,
    AddHolidayComponent,
    SingleHolidayComponent,
    RecommendationsComponent,
    BookingsComponent,
    HolidaysComponent
  ],
  imports: [
    BrowserModule,
    FlexLayoutModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatExpansionModule,
    MatMenuModule,
    MatFormFieldModule,
    MatInputModule,
    MatToolbarModule,
    FormsModule,
    ReactiveFormsModule,
    MatDatepickerModule,
    MatCheckboxModule,
    EditorModule,
    MatGridListModule,
    NgbModule,
    CarouselModule, 
    WavesModule,
    NgbCarouselModule,
    ButtonsModule, CollapseModule,
    ChartsModule,
    CreditCardDirectivesModule,
    MatTableModule,
    StoreModule.forRoot({
      loc: locationReducer
    }),
    NgxWebstorageModule.forRoot(),
    RouterModule.forRoot(
      [ {path: 'home',component: HomeComponent},
        {path: 'account-page', component: AccountPageComponent},
        {path: 'login-page', component: LoginPageComponent},
        {path: 'add-trip', component: AddTripComponent},
        {path: 'wallet', component: WalletComponent},
        {path: 'event', component: EventComponent},
        {path: 'weather', component: WeatherComponent},
        {path: 'cart', component: CartComponent},
        {path: 'add-post', component: AddPostComponent},
        {path: 'single-post/:id', component: SinglePostComponent, canActivate: [AuthGuard]},
        {path: 'cart/:name', component: CartComponent, canActivate: [AuthGuard]},
        {path: 'contact', component: ContactComponent},
        {path: 'user-profile', component: UserProfileComponent, canActivate: [AuthGuard]},
        {path: 'line-chart', component: LineChartComponent},
        {path: 'search-results', component: SearchResultsComponent},
        {path: 'add-holiday', component: AddHolidayComponent},
        {path: 'single-holiday/:id', component: SingleHolidayComponent, canActivate: [AuthGuard]},
        {path: 'recommendations', component: RecommendationsComponent},
        {path: 'bookings/:id', component: BookingsComponent, canActivate: [AuthGuard]},
        {path: 'holidays', component: HolidaysComponent}
      ]
    ),

  ],
  providers: [  ShareDataService,WeatherDataService,
  HomeComponent,
  {provide: HTTP_INTERCEPTORS, useClass: HttpClientInterceptor, multi: true}
  ],

  bootstrap: [AppComponent]
})


export class AppModule { }


