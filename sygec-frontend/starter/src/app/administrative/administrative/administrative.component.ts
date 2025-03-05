import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-administrative',
  templateUrl: './administrative.component.html',
  styleUrls: ['./administrative.component.scss']
})
export class AdministrativeComponent implements OnInit {
  active = 1;

  unActive = false;

  constructor() { }

  ngOnInit(): void { }
}
