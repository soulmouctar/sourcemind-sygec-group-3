// Angular import
import { Component, OnInit } from '@angular/core';

import { RouterModule } from '@angular/router';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { Admin } from '../../../models/admin';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AuthenticationService } from '../../../services/authentication.service';
import { TokenStorageService } from '../../../../_services/token-storage.service';
import { AdminService } from '../../../services/admin.service';

// project import


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export default class LoginComponent implements OnInit {
  // public method
  usernameValue = 'votre mail';
  userPassword = '123456';
  user = {username:''
       ,password:''
}

isLoading = false;

  loginForm!: FormGroup;
  loading = false;
  submitted = false;
  error = '';
  classList!: { toggle: (arg0: string) => void };
  isLoginFailed: boolean | undefined;
  isLoggedIn: boolean | undefined;
  errorMessage: any;
  adminParam: Admin | null | undefined;

  constructor(
    private modal : NgbModal,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService,
    private tokenService:TokenStorageService,
    private adminService:AdminService
  ) {
    // redirect to home if already logged in
    if (this.authenticationService.userValue) {
      this.router.navigate(['/login']);
    }
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    });

    const togglePassword = document.querySelector('#togglePassword');
    const password = document.querySelector('#password');

    togglePassword?.addEventListener('click', () => {
      // toggle the type attribute
      const type = password?.getAttribute('type') === 'password' ? 'text' : 'password';
      password?.setAttribute('type', type);

      // toggle the icon
      this.classList.toggle('icon-eye-off');
    });
  }

  // convenience getter for easy access to form fields
  get f() {
    return this.loginForm.controls;
  }

  onSubmit() {
    this.isLoading = true;
    // Simulate API call
    setTimeout(() => {
      this.isLoading = false;
    }, 2000);
  
    this.submitted = true;

    // stop here if form is invalid
    if (this.loginForm.invalid) {
      return;
    }

    this.error = '';
    this.loading = true;
    // this.authenticationService
    //   .login(this.f?.['username']?.value, this.f?.['password']?.value)
    //   .subscribe(res => {
    //     console.log(res);
    //     this.tokenService.saveToken(res.token);
    //     this.router.navigate(['/dashboard/analytics']);
    //   });

      this.authenticationService.login(this.f?.['username']?.value, this.f?.['password']?.value).then(
        (data: any) => {
          this.tokenService.saveToken(data.token);
          this.tokenService.saveUser(
            this.tokenService.getDecodedAccessToken(data.token).sub
          );
          this.tokenService.saveRole(
            this.tokenService.getDecodedAccessToken(data.token).roles
          );
          // this.tokenStorage.saveUser(data);
  
          this.isLoginFailed = false;
          this.isLoggedIn = true;
          // this.roles = this.tokenStorage.getUser().roles;
          this.reloadPage();
          this.router.navigate(["/home"]);
        },
        (err) => {
          this.errorMessage = err.error.message;
          this.isLoginFailed = true;
        }
      )
  }

  reloadPage(): void {
    window.location.reload();
  }


  openModal(selectedModal: NgbModal |any) {
    console.log(this.f?.['username']?.value)
  //  this.adminParam = adminPassed;
  this.adminService.getAdminByEmail(this.f?.['username']?.value).then(res => {
    console.log(res)
    if(res?.firstConnection !== true && res?.roles[0] !=="ADMIN"){
      this.modal.open(selectedModal, {
        centered: true,
        size: "md",
        backdrop: "static",
      });
      this.f?.['username']?.disable()
    }else{
      this.onSubmit();
    }
  })
    
  }

  closeModal() {
    this.modal.dismissAll();
  }

  onChangePassword(){
    
      this.adminService.changeEmailAndPassword(this.f?.['username']?.value, this.f?.['password']?.value).subscribe(res => {

         // stop here if form is invalid
     if (this.loginForm.invalid) {
      return;
    }

    this.error = '';
    this.loading = true;
    // this.authenticationService
    //   .login(this.f?.['username']?.value, this.f?.['password']?.value)
    //   .subscribe(res => {
    //     console.log(res);
    //     this.tokenService.saveToken(res.token);
    //     this.router.navigate(['/dashboard/analytics']);
    //   });

      this.authenticationService.login(res.email, this.f?.['password']?.value).then(
        (data: any) => {
          this.tokenService.saveToken(data.token);
          this.tokenService.saveUser(
            this.tokenService.getDecodedAccessToken(data.token).sub
          );
          this.tokenService.saveRole(
            this.tokenService.getDecodedAccessToken(data.token).aud
          );
          // this.tokenStorage.saveUser(data);
  
          this.isLoginFailed = false;
          this.isLoggedIn = true;
          // this.roles = this.tokenStorage.getUser().roles;
          this.reloadPage();
          this.router.navigate(["/home"]);
        },
        (err) => {
          this.errorMessage = err.error.message;
          this.isLoginFailed = true;
        }
      )
  
        
      })

    
  }
}
