import { Pipe, PipeTransform } from '@angular/core';
import { Station } from './models/station';
@Pipe({
  name: 'filter'
})
export class FilterPipe implements PipeTransform {
  transform(items: Station[], searchText: string): any[] {
    if(!items) return [];
    if(!searchText) return items;
searchText = searchText.toString().toLowerCase();
return items.filter( it => {
      return it.properties.NOM_STAT.toString().toLowerCase().includes(searchText);
    });
   }
}