import { Component } from '@angular/core';
import { DataService } from './data.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  providers: [DataService],
  styleUrls: ['./app.component.sass']
})
export class AppComponent {
  title = 'app';
  lat: number = 45.505168;
  lng: number = -73.576488;
}
