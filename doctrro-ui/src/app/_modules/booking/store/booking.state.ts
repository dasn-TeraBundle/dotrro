import {DoctorSearchResponse, SearchCriteria} from '../_models';
import {createSelector} from '@ngrx/store';
import {selectFeatureBooking} from '../../../store/state';

export interface BookingState {
  srchCriteria: SearchCriteria;
  doctors: DoctorSearchResponse[];
}

export const selectSearchCriteria = createSelector(selectFeatureBooking, state => state.srchCriteria);
export const selectDoctors = createSelector(selectFeatureBooking, state => state.doctors);
