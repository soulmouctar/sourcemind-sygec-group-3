import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-tables-de-reference',
  templateUrl: './tables-de-reference.component.html',
  styleUrls: ['./tables-de-reference.component.css']
})
export class TablesDeReferenceComponent implements OnInit {
  active = 1;

  unActive = false;

  constructor() { }

  ngOnInit(): void { }
}
