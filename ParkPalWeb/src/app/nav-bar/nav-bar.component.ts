
import { Component, OnInit, style } from '@angular/core';
import { DataService } from '../data.service';
import { Input } from '@angular/core/src/metadata/directives';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.sass']
})
export class NavBarComponent implements OnInit {

  origin:string;
  destination:string;
  directionTrue: number;
  methodOfTransp: string;
  routePreference: string;

  constructor(private data: DataService) { }

  setType(transportationType:string,button:number){
    this.methodOfTransp = transportationType;
    var el = document.getElementsByClassName("transButton");
    el[button];
    console.log("Type being changed: "+this.methodOfTransp);
  }

  private toggleClicked() {
    if (this.routePreference == "FASTEST") {
      this.routePreference = "SHORTEST";
    }
    else {
      this.routePreference = "FASTEST";
    }
    console.log('the toggle: ' + this.routePreference);
  }
  
  ngOnInit() {
    this.data.currentOrigin.subscribe(origin => this.origin=origin)
    this.data.currentDestination.subscribe(destination => this.destination=destination)
    this.data.currentNumber.subscribe(directionTrue => this.directionTrue = directionTrue)
    this.data.currentTransport.subscribe(transport => this.methodOfTransp = transport)
    this.data.currentRoutePref.subscribe(routePref => this.routePreference = routePref)
  }

  newMessage(origin:string,destination:string){
    if(origin && destination){
      this.data.changeOrigin(origin)
      this.data.changeDestination(destination)
      this.data.changeNumber(1)
      this.data.changeMethodOfTransp(this.methodOfTransp)
      this.data.changeMethodOfRoutePref(this.routePreference)
    }
  }

  showList(){
    this.data.changeHideList();
  }
}
