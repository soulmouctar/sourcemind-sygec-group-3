import { Component, AfterViewInit, ViewChild } from '@angular/core';
import * as Chartist from 'chartist';
import { ChartType, ChartEvent } from 'ng-chartist';

export interface Chart {
    type: ChartType;
    data: Chartist.IChartistData;
    options?: any;
    responsiveOptions?: any;
    events?: ChartEvent;
}

// Données mockées pour remplacer les imports JSON
const data2 = {
  data: [
    { name: 'Company A', status: 'Active', company: 'Tech Corp' },
    { name: 'Company B', status: 'Inactive', company: 'Dev Inc' }
  ]
};

const data = {
  Pie: {
    series: [20, 10, 30, 40],
    labels: ['Series 1', 'Series 2', 'Series 3', 'Series 4']
  }
};

@Component({
  templateUrl: './starter.component.html'
})
export class StarterComponent implements AfterViewInit {
  subtitle: string;
  editing: { [key: string]: boolean } = {};
  rows: any[] = [];
  temp: any[] = [];
  loadingIndicator = true;
  reorderable = true;

  columns = [
    { prop: 'name' }, 
    { name: 'Status' }, 
    { name: 'Company' }
  ];

  // Dashboard line chart
  public lineChartData: Array<any> = [
    { data: [0, 130, 80, 70, 180, 105, 250], label: 'Site A' },
    { data: [0, 100, 60, 200, 150, 90, 150], label: 'Site B' }
  ];

  public lineChartLabels: Array<string> = [
    '2010', '2011', '2012', '2013', '2014', '2015', '2016'
  ];

  public lineChartOptions = {
    responsive: true
  };

  public lineChartColors: Array<any> = [
    {
      backgroundColor: 'rgba(25,118,210,0.0)',
      borderColor: 'rgba(25,118,210,1)',
      pointBackgroundColor: 'rgba(25,118,210,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(25,118,210,0.5)'
    },
    {
      backgroundColor: 'rgba(38,218,210,0.0)',
      borderColor: 'rgba(38,218,210,1)',
      pointBackgroundColor: 'rgba(38,218,210,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(38,218,210,0.5)'
    }
  ];

  public lineChartLegend = false;
  public lineChartType = 'line';

  // Donut chart
  donuteChart1: Chart = {
    type: 'Pie',
    data: data.Pie,
    options: {
      height: 200,
      donut: true,
      showLabel: false,
      donutWidth: 30
    }
  };
products: any;

  constructor() {
    this.subtitle = 'This is some text within a card block.';
    this.rows = data2.data;
    this.temp = [...data2.data];
    setTimeout(() => {
      this.loadingIndicator = false;
    }, 1500);
  }

  ngAfterViewInit() {}

  // Table methods
  updateFilter(event: any): void {
    const val = event.target.value.toLowerCase();
    
    const temp = this.temp.filter(d => 
      d.name.toLowerCase().includes(val) || !val
    );

    this.rows = temp;
  }

  updateValue(event: any, cell: string, rowIndex: number): void {
    console.log('inline editing rowIndex', rowIndex);
    this.editing[`${rowIndex}-${cell}`] = false;
    this.rows[rowIndex][cell] = event.target.value;
    this.rows = [...this.rows];
    console.log('UPDATED!', this.rows[rowIndex][cell]);
  }
}