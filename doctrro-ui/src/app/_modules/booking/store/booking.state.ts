import {DoctorSearchResponse, SearchFilters} from '../_models';
import {createSelector} from '@ngrx/store';
import {selectFeatureBooking} from '../../../store/state';

export interface SearchState {
  filters: SearchFilters;
  doctors: DoctorSearchResponse[];
}

export interface BookingState {
  search: SearchState;
}

export const selectSearchFilters = createSelector(selectFeatureBooking, state => state.search.filters);
export const selectDoctors = createSelector(selectFeatureBooking, state => state.search.doctors);
