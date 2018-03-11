import { Component, OnInit } from '@angular/core';
import { DataService } from '../data.service';
import { log } from 'util';
import { } from '@types/googlemaps';
import { Stations } from '../../assets/stations-data'
import { Station } from '../models/station'

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
  methodOfTransp: string;
  routePreference: string;

  map:any;
  directionsService: google.maps.DirectionsService;
  directionsDisplay: google.maps.DirectionsRenderer;

  stations: Station[];

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
    document.getElementById("routeoptions");	
    document.getElementById("routeoptions").innerHTML = "";

    let directionsPanel = document.getElementById("routeoptions");

    

    
    this.directionsDisplay.setPanel(directionsPanel);

    let start = this.origin;
    let end = this.destination;
    let parkAndRide;
    let transport;
    if (this.methodOfTransp == "PNR") {
      parkAndRide = true;
      transport = "TRANSIT";
    } else {
      transport = this.methodOfTransp;
    }
    let preference = this.routePreference;

    var request = {
      origin: start,
      destination: end,
      travelMode: google.maps.TravelMode[transport],
      provideRouteAlternatives: true
    };
    this.directionsService.route(request, (result, status) => {
      if (status == google.maps.DirectionsStatus.OK) {
        // below, finding shortest distance for driving mode of transport
        if (transport == 'DRIVING' && preference == 'SHORTEST') {
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
        else { // below, manually ensures to display only fastest route b/c route alternatives param is on for request
          var fastestRoute = result.routes[0];
          result.routes.length = 0;
          result.routes.push(fastestRoute);
          console.log(fastestRoute.legs["0"].steps["0"].end_point.lat());
          console.log(fastestRoute.legs["0"].steps["0"].end_point.lng());
        }
        this.directionsDisplay.setDirections(result);
      } else if (google.maps.DirectionsStatus == 'ZERO_RESULTS') {
        // this.directionsDisplay.setDirections(null);
        console.log("impossible");
        document.getElementById("routeoptions").innerHTML = "Cannot get directions with the current mode of transportation"
      }
      else {
        console.log("misspelled");
        document.getElementById("routeoptions").innerHTML = "Invalid Destination or Current Location"
      }
    })
  }


  ngOnInit() {

    this.stations = Stations;

    this.getClosestMetroWithParking("test");

    let mapProp = {
        center: new google.maps.LatLng(45.504386, -73.576659),
        zoom: 11,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    this.directionsDisplay = new google.maps.DirectionsRenderer;
    this.directionsService = new google.maps.DirectionsService;
    this.map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
    this.directionsDisplay.setMap(this.map);

    this.data.currentOrigin.subscribe(origin => this.origin=origin)   
    this.data.currentDestination.subscribe(destination => this.destination=destination) 
    this.data.currentNumber.subscribe(directionTrue => this.directionTrue=directionTrue)
    this.data.currentTransport.subscribe(transport => this.methodOfTransp = transport)
    this.data.currentRoutePref.subscribe(routePref => this.routePreference = routePref)

    var inputFrom = document.getElementById('from');
    var inputTo = document.getElementById('to');
    var autocompleteFrom = new google.maps.places.Autocomplete(inputFrom);
    var autocompleteTo = new google.maps.places.Autocomplete(inputTo);
    autocompleteFrom.bindTo('bounds', this.map);
    autocompleteTo.bindTo('bounds', this.map);
  }

  getClosestMetroWithParking (currentLocation) {
    let closestStation;
    let minDistance = 0;
    for (let station of this.stations) {
      
    }
  }

  distanceBetweenPoints(one, two) {
    var R = 6371e3; // metres
    var φ1 = one[0].toRadians();
    var φ2 = two[0].toRadians();
    var Δφ = this.toRadians(two[0]-one[0]);
    var Δλ = this.toRadians(two[1]-one[1]);

    var a = Math.sin(Δφ/2) * Math.sin(Δφ/2) +
            Math.cos(φ1) * Math.cos(φ2) *
            Math.sin(Δλ/2) * Math.sin(Δλ/2);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

    return R * c;
  }

  toRadians(degrees) {
    return degrees * Math.PI / 180;
  };
}
