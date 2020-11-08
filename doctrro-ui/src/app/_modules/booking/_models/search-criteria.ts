import {Location} from "./location";

export class SearchCriteria {
  loc: Location;
  radius: number;
  speciality: string;

  constructor() {
    this.loc = new Location();
  }

}
