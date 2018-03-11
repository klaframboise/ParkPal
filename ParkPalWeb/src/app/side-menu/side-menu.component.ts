import { Component, OnInit } from '@angular/core';
import { Stations } from '../../assets/stations-data'
import { Station } from '../models/station'
import { DataService } from '../data.service'
import { FilterPipe} from '../filter.pipe';

@Component({
  selector: 'app-side-menu',
  templateUrl: './side-menu.component.html',
  styleUrls: ['./side-menu.component.sass']
})
export class SideMenuComponent implements OnInit {
  
  stations:Station[]
  hideProperties
  hideList

  constructor(private data: DataService) { }

  ngOnInit() {
    this.stations = Stations;
    this.hideList = this.data.hideList; 
  }

  popUp(){
    console.log("click worked")
  }
}
