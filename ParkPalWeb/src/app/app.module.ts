import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { MapComponent } from './map/map.component';
import { SideMenuComponent } from './side-menu/side-menu.component';
import { AppDirBarComponent } from './app-dir-bar/app-dir-bar.component';

@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    MapComponent,
    SideMenuComponent,
    AppDirBarComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
