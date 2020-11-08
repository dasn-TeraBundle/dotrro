import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.scss']
})
export class BookingComponent implements OnInit {

  private data: any = {};

  constructor(private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.data = {...this.route.snapshot.params};
  }

}
