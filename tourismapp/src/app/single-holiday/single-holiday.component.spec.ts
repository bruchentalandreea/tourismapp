import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SingleHolidayComponent } from './single-holiday.component';

describe('SingleHolidayComponent', () => {
  let component: SingleHolidayComponent;
  let fixture: ComponentFixture<SingleHolidayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SingleHolidayComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SingleHolidayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
