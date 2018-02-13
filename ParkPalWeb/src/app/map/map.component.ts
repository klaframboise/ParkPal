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

  map:any;

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
    let directionsPanel = document.getElementById("routeoptions");

    let directionsDisplay = new google.maps.DirectionsRenderer();
    let directionsService = new google.maps.DirectionsService();

    directionsDisplay.setMap(this.map);
    directionsDisplay.setPanel(directionsPanel);

    let start = this.origin;
    let end = this.destination;
    let transport = this.methodOfTransp

    var request = {
      origin: start,
      destination: end,
      travelMode: transport
    };
    directionsService.route(request, function(result, status){
      if (status == 'OK') {
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

    this.map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
    this.data.currentOrigin.subscribe(origin => this.origin=origin)   
    this.data.currentDestination.subscribe(destination => this.destination=destination) 
    this.data.currentNumber.subscribe(directionTrue => this.directionTrue=directionTrue)
    this.data.currentTransport.subscribe(transport => this.methodOfTransp=transport)
  }
}
