
import { Component, OnInit, style } from '@angular/core';
import { DataService } from '../data.service';
import { Input } from '@angular/core/src/metadata/directives';
import { CookieService } from 'ngx-cookie-service';



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
  cookiesFull: boolean;
  cookiesList: string[] = new Array();

  constructor(private data: DataService, private cookieService: CookieService) { }

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

  initializeCookiesList() {
    for (var x in this.cookieService.getAll()) {
      this.cookiesList.push(this.cookieService.get(x));
    }
  }
  ngOnInit() {
    // below, initializes cookiesList
    this.initializeCookiesList();

    this.data.currentOrigin.subscribe(origin => this.origin=origin)
    this.data.currentDestination.subscribe(destination => this.destination=destination)
    this.data.currentNumber.subscribe(directionTrue => this.directionTrue = directionTrue)
    this.data.currentTransport.subscribe(transport => this.methodOfTransp = transport)
    this.data.currentRoutePref.subscribe(routePref => this.routePreference = routePref)
  }

  // this function is called whenever seaarch button is pressed to update cookies
  updateCookies(origin: string, destination: string) {
    if (this.cookiesList.length >= 5) {
      if (this.cookiesList.indexOf(origin) == -1) {
        this.cookiesList.splice(0, 1);
        this.cookiesList.push(origin);
      }
      if (this.cookiesList.indexOf(destination) == -1) {
        this.cookiesList.splice(0, 1);
        this.cookiesList.push(destination);
      }
    }
    else { // if list is not full, simply check if origin/destination is already within list
      // if not, then add them
      if (this.cookiesList.indexOf(origin) == -1) {
        this.cookiesList.push(origin);
      }
      if (this.cookiesList.indexOf(destination) == -1) {
        this.cookiesList.push(destination);
      }
    }

    this.cookieService.deleteAll();
    for (let i in this.cookiesList) {
      var index = +i;
      index++;
      this.cookieService.set(String(index), this.cookiesList[i]);
    }
  }

  newMessage(origin:string,destination:string){
    if(origin && destination){
      this.data.changeOrigin(origin)
      this.data.changeDestination(destination)
      this.data.changeNumber(1)
      this.data.changeMethodOfTransp(this.methodOfTransp)
      this.data.changeMethodOfRoutePref(this.routePreference)
      this.updateCookies(origin, destination)
    }
  }
  showDropDown(value:string) {
    console.log((<HTMLTextAreaElement>document.getElementById(value)).style.display);
    var my_disply = (<HTMLTextAreaElement>document.getElementById(value)).style.display;
    if (my_disply == "block") {
      (<HTMLTextAreaElement>document.getElementById(value)).style.display = "none";
      console.log("i am in block and becoming none");
    }
    else
      document.getElementById(value).style.display = "block";
  }
}

