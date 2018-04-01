
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
  distanceMetric = true;
  cookiesList: string[] = new Array();
  favorites: string[] = new Array();
  nightTheme:HTMLElement=null;
  selectedFavorite: any;
  lastFocusedInput: string;
  favoriteMode: string;
  cookieValue = 'UNKNOWN';

  constructor(private data: DataService, private cookieService: CookieService) {
    this.lastFocusedInput = "origin";
    this.selectedFavorite = "placeholderfavorites";
    this.favoriteMode = "add";
   }

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

  private toggleDistance() {
    this.distanceMetric = !this.distanceMetric;
    this.data.changeUnitOfDistance(this.distanceMetric);
  }

  initializeCookiesList() {
    this.cookiesList = [];
    this.favorites = [];
  //  this.nightTheme= document.getElementById("nav");
   // console.log("initalize cookies = " + this.nightTheme.className);
    for (var x in this.cookieService.getAll()) {
      console.log(this.cookieService.get(x));
      if (this.cookieService.get(x).startsWith("<H>")){
        this.cookiesList.push(this.cookieService.get(x));
      }
      if (this.cookieService.get(x).startsWith("<F>")){
        this.favorites.push(this.cookieService.get(x));
      }
    }
  }

  initalizeNavBar(){
    const cookieExists: boolean = this.cookieService.check('Test');
     if(cookieExists == false){
    this.cookieService.set( 'Test', 'light-mode' );
    
  }
  this.cookieValue = this.cookieService.get('Test');
    if(this.cookieValue == "light-mode"){
      var night = document.getElementById("nav");
      night.className = "light-mode";
      this.cookieService.set( 'Test', 'dark-mode' );
    }
    else{
      var night = document.getElementById("nav");
      night.className = "dark-mode";
      this.cookieService.set( 'Test', 'light-mode' );
    }
  }

  ngOnInit() {
    // below, initializes cookiesList
    this.initializeCookiesList();
    this.initalizeNavBar();

    this.data.currentOrigin.subscribe(origin => this.origin=origin)
    this.data.currentDestination.subscribe(destination => this.destination=destination)
    this.data.currentNumber.subscribe(directionTrue => this.directionTrue = directionTrue)
    this.data.currentTransport.subscribe(transport => this.methodOfTransp = transport)
    this.data.currentRoutePref.subscribe(routePref => this.routePreference = routePref)
  }

  // this function is called whenever search button is pressed to update cookies
  updateCookies(origin: string, destination: string) {
    origin = "<H>" + origin;
    destination = "<H>" + destination;
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

    for (var x in this.cookieService.getAll()) {
      if (this.cookieService.get(x).startsWith("<H>")){
        this.cookieService.delete(x);
      }
    }
    
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

  getSelectedFavorite() {
    if (this.selectedFavorite == "removefavorite") {
      this.favoriteMode = "remove";
    } else if (this.selectedFavorite == "addorigin") {
      var fromValue = (<HTMLTextAreaElement>document.getElementById("from")).value;
      if (fromValue != "") 
        this.addFavorite(fromValue);
    } else if (this.selectedFavorite == "adddestination") {
      var toValue = (<HTMLTextAreaElement>document.getElementById("to")).value;
      if (toValue != "")
        this.addFavorite(toValue);
    } else {
      if (this.favoriteMode == "remove") {
        this.cookieService.delete("<F>" + this.selectedFavorite);
        this.initializeCookiesList();
        this.favoriteMode = "add";
        return;
      }
      if (this.lastFocusedInput == "origin") {
        this.updateAddress(String(this.selectedFavorite), "from");
      } else {
        this.updateAddress(String(this.selectedFavorite), "to");
      }
    }
  }

  addFavorite(fav: string) {
    console.log(fav);
    for (var x in this.favorites) {
      if (this.favorites[x].substring(3) == fav){
        alert("This favorite already exists");
        return;
      }
    }
    if (this.favorites.length >= 5) {
      for (var x in this.cookieService.getAll()) {
        if (this.cookieService.get(x).startsWith("<F>")){
          this.cookieService.delete(x);
        }
      }
      this.favorites.reverse();
      var popped = this.favorites.pop();
      this.cookieService.delete(popped);
      this.favorites.reverse();
    }
    fav = "<F>" + fav;
    this.favorites.push(fav);
    for (var x in this.favorites) {
      this.cookieService.set(this.favorites[x], this.favorites[x]);
    }
  }

  setFocus(focus: string){
    this.lastFocusedInput = focus;
  }

  // JS function responsible for hiding/unhiding history dropdown
  showDropDown(value:string) {
    var my_disply = (<HTMLTextAreaElement>document.getElementById(value)).style.display;
    if (my_disply == "block") {
      (<HTMLTextAreaElement>document.getElementById(value)).style.display = "none";
    }
    else
      document.getElementById(value).style.display = "block";
  }

  updateAddress(address: string, element: string) {
    (<HTMLTextAreaElement>document.getElementById(element)).value = address;
  }
  showList(){
    this.data.changeHideList();
  }

  //method to allow for night mode by changing between nav and nav.dark-mode
  toggleDarkLight() {

    var themeMode = this.cookieService.get('Test');
    if(themeMode == "light-mode"){
      var night = document.getElementById("nav");
      night.className = "dark-mode";
      themeMode ="dark-mode";
      this.cookieService.set( 'Test', 'dark-mode' );
    }else{

      var night = document.getElementById("nav");
      night.className = "light-mode";
      this.cookieService.set( 'Test', 'light-mode' );
      themeMode="light-mode";
    }

  }



  showQuestion(){
    console.log("Button pressed for question");
    this.data.changeHideQuestion();
  }
}

