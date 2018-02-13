import { Component, OnInit } from '@angular/core';
import { DataService } from '../data.service';
import { log } from 'util';

declare const google: any;

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.sass']
})


export class MapComponent implements OnInit {
  
  origin:string;
  destination:string;
  directionTrue:number;
  methodOfTransp:string;

  check(directionTrue:number){
    if(this.directionTrue==1){
      console.log("Checking if true");
        this.findDirection();
     }
     this.data.changeNumber(0);

  }

 
  constructor(private data: DataService) { }



  findDirection(){
    console.log("Finding Route to: "+this.origin+this.methodOfTransp)
    let mapProp = {
      mapTypeId: google.maps.MapTypeId.ROADMAP
  };
    let map = new google.maps.Map(document.getElementById("googleMap"));
    document.getElementById("routeoptions");
    document.getElementById("routeoptions").innerHTML = "";
    let directionsPanel = document.getElementById("routeoptions");

    let directionsDisplay = new google.maps.DirectionsRenderer();
    let directionsService = new google.maps.DirectionsService();

    directionsDisplay.setMap(map);
    directionsDisplay.setPanel(directionsPanel);

    let start = this.origin;
    let end = this.destination;
    let transport = this.methodOfTransp

    var request = {
      origin: start,
      destination: end,
      travelMode: transport,
      provideRouteAlternatives: true
    };
    directionsService.route(request, function(result, status){
      if (status == 'OK') {
        // below, finding shortest distance for driving mode of transport
        if(transport == 'DRIVING'){
            var shortestDist = result.routes[0].legs[0].distance.value;
            var shortestDistRoute = result.routes[0];
            // below, for-each loop to compare distance of all suggested routes and find shortest one
            result.routes.forEach(function (value){
              if(value.legs[0].distance.value < shortestDist){
                value.legs[0].distance.value = shortestDist;
                shortestDistRoute = value;
              }
            });
            // below, reset the routes array to only contain the shortest distance route
            result.routes.length = 0;
            result.routes.push(shortestDistRoute);
        }
        directionsDisplay.setDirections(result);
      } else {
        console.log("no");
      }
    })
  }



  ngOnInit() {

    let mapProp = {
        center: new google.maps.LatLng(45.504386, -73.576659),
        zoom: 11,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    let map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
    this.data.currentOrigin.subscribe(origin => this.origin=origin)   
    this.data.currentDestination.subscribe(destination => this.destination=destination) 
    this.data.currentNumber.subscribe(directionTrue => this.directionTrue=directionTrue)
    this.data.currentTransport.subscribe(transport => this.methodOfTransp=transport)
  }
}
