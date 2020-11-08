import {Component, OnDestroy, OnInit} from '@angular/core';
import {DoctorSearchResponse, SearchCriteria} from "../../_models";
import {SearchService} from "../../_services/search.service";
import {Observable} from "rxjs";
import {Store} from "@ngrx/store";
import {getSearchCriteria, updateSearchCriteria} from "../../store/search/search.action";
import {selectDoctors, selectSearchCriteria} from "../../store/booking.state";
import {fetchDoctors, updateDoctors} from "../../store/doctors/doctors.action";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit, OnDestroy {

  criteria: SearchCriteria;
  doctors$: Observable<DoctorSearchResponse[]>;

  private criteriaSub: any;
  private isCriteriaUpdated = true;

  constructor(private searchService: SearchService,
              private store: Store) {
  }

  ngOnInit(): void {
    this.criteriaSub = this.store.select(selectSearchCriteria).subscribe(criteria => {
      console.log(criteria);
      if (criteria === undefined) {
        const cr = new SearchCriteria();
        cr.loc.lat = -73.9667;
        cr.loc.lon = 40.78;
        cr.radius = 5;

        this.store.dispatch(updateSearchCriteria({criteria: cr}));
      } else {
        this.criteria = {...criteria};
      }
    });

    this.doctors$ = this.store.select(selectDoctors);
  }

  onChangeSearchParams(): void {
    this.isCriteriaUpdated = true;
    this.store.dispatch(updateSearchCriteria({criteria: this.criteria}));
  }

  searchDoctor(): void {
    if (this.isCriteriaUpdated) {
      this.store.dispatch(fetchDoctors({criteria: this.criteria}));
    }

    this.isCriteriaUpdated = false;
  }

  ngOnDestroy(): void {
    this.criteriaSub.unsubscribe();
  }
}
