
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



  constructor(private data: DataService) { }


  
  ngOnInit() {
    this.data.currentOrigin.subscribe(origin => this.origin=origin)
    this.data.currentDestination.subscribe(destination => this.destination=destination)
    this.data.currentNumber.subscribe(directionTrue => this.directionTrue = directionTrue)
  }

  newMessage(origin:string,destination:string){
    this.data.changeOrigin(origin)
    this.data.changeDestination(destination)
    this.data.changeNumber(1)
  }


}
