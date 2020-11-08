import {Component, Input, OnInit} from '@angular/core';
import {DoctorSearchResponse} from "../../_models";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-doctor',
  templateUrl: './doctor.component.html',
  styleUrls: ['./doctor.component.scss']
})
export class DoctorComponent implements OnInit {

  @Input() doctor: DoctorSearchResponse;

  ratingLabel: string;

  constructor(private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    if (this.doctor.rating === 0) {
      this.ratingLabel = 'No ratings';
    } else {
      this.ratingLabel = `${this.doctor.rating}`;
    }
  }

  goToBooking(facilityId: string): void {
    const url = `./book/fid/${facilityId}/did/${this.doctor.regId}`;
    // this.router.navigate(['./book'], {relativeTo: this.route, state: {did: this.doctor.regId}});
    this.router.navigate([url], {relativeTo: this.route});
  }
}
