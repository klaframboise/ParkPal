import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';


@Injectable()

export class DataService {

  private originSource = new BehaviorSubject<string>("");
  private destinationSource = new BehaviorSubject<string>("");
  private numberSource = new BehaviorSubject<number>(0)

  currentOrigin = this.originSource.asObservable();
  currentDestination = this.destinationSource.asObservable();
  currentNumber = this.numberSource.asObservable();

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
}