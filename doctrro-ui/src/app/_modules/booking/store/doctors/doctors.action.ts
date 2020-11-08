import { createAction, props } from '@ngrx/store';
import {DoctorSearchResponse, SearchCriteria} from '../../_models';

export const getDoctors = createAction(
  '[DOCTORS] GET'
);

export const fetchDoctors = createAction(
  '[DOCTORS] FETCH',
  props<{ criteria: SearchCriteria }>()
);

export const updateDoctors = createAction(
  '[DOCTORS] UPDATE',
  props<{ doctors: DoctorSearchResponse[] }>()
);
