import { Component } from '@angular/core';


@Component({
  selector: 'app-ngbd-accordion-basic',
  templateUrl: 'accordion.component.html'
})
export class NgbdAccordionBasicComponent {
  beforeChange($event: any) {
    if ($event.panelId === 'preventchange-2') {
      $event.preventDefault();
    }

    if ($event.panelId === 'preventchange-3' && $event.nextState === false) {
      $event.preventDefault();
    }
  }
}
