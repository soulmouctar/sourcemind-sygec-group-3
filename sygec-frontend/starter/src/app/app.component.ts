import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from '../_services/token-storage.service';
import { AdminService } from './services/admin.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'app';

  isLoggedIn: boolean = false;
  // constructor
  constructor(
    private router: Router,
      private tokenStorageService: TokenStorageService,
      private adminService:AdminService) {}
 
  // life cycle event
  ngOnInit() {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if(this.isLoggedIn){
  
      this.router.navigate(['/starter']);
    }else{
      this.router.navigate(['/authentication/login']);
      
    }
    
    if (this.tokenStorageService.getToken() == null || this.tokenStorageService.getToken() == undefined || this.tokenStorageService.getToken() == '') {
      this.router.navigate(['/authentication/login']);
    }
  }
}



