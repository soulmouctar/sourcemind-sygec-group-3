import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../../services/authentication.service';
import { TokenStorageService } from '../../../../_services/token-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-loyout',
  templateUrl: './loyout.component.html',
  styleUrls: ['./loyout.component.scss']
})
export class LoyoutComponent implements OnInit {

  constructor(
    private authentocationService : AuthenticationService,
    private tokenStorageService: TokenStorageService,
    private router: Router) {

  }

  ngOnInit() {
   this.tokenStorageService.signOut()
 //   this.authentocationService.logout();
    this.router.navigate(['/login']);
  }

}