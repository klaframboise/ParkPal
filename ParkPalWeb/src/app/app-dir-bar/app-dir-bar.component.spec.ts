import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppDirBarComponent } from './app-dir-bar.component';

describe('AppDirBarComponent', () => {
  let component: AppDirBarComponent;
  let fixture: ComponentFixture<AppDirBarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppDirBarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppDirBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
