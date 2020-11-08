import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent, SearchComponent, BookingComponent } from './_components';
import {BookingRoutingModule} from "./booking-routing.module";
import {FormsModule} from "@angular/forms";
import {SharedMaterialModule} from "../shared/shared-material/shared-material.module";
import {HttpClientModule} from "@angular/common/http";
import {SearchService} from "./_services/search.service";
import * as fromSearchCriteria from './store/search/search.reducer';
import * as fromDoctors from './store/doctors/doctors.reducer';
import {StoreModule} from "@ngrx/store";
import {EffectsModule} from "@ngrx/effects";
import {DoctorsEffects} from "./store/doctors/doctors.effects";
import { DoctorComponent } from './_components/doctor/doctor.component';
import {StarRatingModule} from 'angular-star-rating';



@NgModule({
  declarations: [SearchComponent, HomeComponent, BookingComponent, DoctorComponent],
  imports: [
    CommonModule,
    HttpClientModule,
    FormsModule,
    SharedMaterialModule,
    StarRatingModule.forRoot(),

    StoreModule.forFeature('booking',{
      srchCriteria: fromSearchCriteria.reducer,
      doctors: fromDoctors.reducer
    }),
    EffectsModule.forFeature([DoctorsEffects]),

    BookingRoutingModule
  ],
  providers: [SearchService]
})
export class BookingModule { }
