import { Component, OnInit } from '@angular/core';

declare const google: any;

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.sass']
})
export class MapComponent implements OnInit {

  constructor() { }

  ngOnInit() {
    let mapProp = {
        center: new google.maps.LatLng(45.504987, -73.577260),
        zoom: 5,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    let map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
  }

}
