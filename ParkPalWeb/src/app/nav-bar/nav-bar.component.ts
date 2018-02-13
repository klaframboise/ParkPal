
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

  constructor(private data: DataService) { }

  setType(transportationType:string,button:number){
    this.methodOfTransp = transportationType;
    var el = document.getElementsByClassName("transButton");
    el[button]
    console.log("Type being changed: "+this.methodOfTransp);
  }
  
  ngOnInit() {
    this.data.currentOrigin.subscribe(origin => this.origin=origin)
    this.data.currentDestination.subscribe(destination => this.destination=destination)
    this.data.currentNumber.subscribe(directionTrue => this.directionTrue = directionTrue)
    this.data.currentTransport.subscribe(transport => this.methodOfTransp=transport)
  }

  newMessage(origin:string,destination:string){
    this.data.changeOrigin(origin)
    this.data.changeDestination(destination)
    this.data.changeNumber(1)
    this.data.changeMethodOfTransp(this.methodOfTransp)
  }


}
