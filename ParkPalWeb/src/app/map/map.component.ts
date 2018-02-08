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
        center: new google.maps.LatLng(45.504386, -73.576659),
        zoom: 5,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    let map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
    
    let directionsDisplay = new google.maps.DirectionsRenderer();
    let directionsService = new google.maps.DirectionsService();

    directionsDisplay.setMap(map);

    let start = "McGill University, Montreal";
    let end = "Toronto";
    var request = {
      origin: start,
      destination: end,
      travelMode: 'DRIVING'
    };
    directionsService.route(request, function(result, status){
      if (status == 'OK') {
        directionsDisplay.setDirections(result);
      } else {
        console.log("no");
      }
    })
  }

}
