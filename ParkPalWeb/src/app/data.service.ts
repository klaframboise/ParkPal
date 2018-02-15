import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';


@Injectable()

export class DataService {

  private originSource = new BehaviorSubject<string>("");
  private destinationSource = new BehaviorSubject<string>("");
  private numberSource = new BehaviorSubject<number>(0)
  private methodOfTransp = new BehaviorSubject<string>("DRIVING");
  private methodOfRoutePref = new BehaviorSubject<string>("FASTEST");

  currentOrigin = this.originSource.asObservable();
  currentDestination = this.destinationSource.asObservable();
  currentNumber = this.numberSource.asObservable();
  currentTransport = this.methodOfTransp.asObservable();
  currentRoutePref = this.methodOfRoutePref.asObservable();

  constructor() { }


  changeOrigin(origin: string) {
    this.originSource.next(origin)
  }
  changeDestination(destination: string) {
    this.destinationSource.next(destination)
  }

  changeNumber(directionTrue: number){
    this.numberSource.next(directionTrue)
  }

  changeMethodOfTransp(transport: string){
    this.methodOfTransp.next(transport)
  }

  changeMethodOfRoutePref(routePreference: string) {
    this.methodOfRoutePref.next(routePreference)
  }
}
