import { Component, Host, Input, OnInit } from '@angular/core';
import { Admin } from '../../../../models/admin';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AdminService } from '../../../../services/admin.service';
import Swal from 'sweetalert2';
import { adminListComponent } from '../../list/admin-list/admin-list.component';

@Component({
  selector: 'app-admin-form',
  templateUrl: './admin-form.component.html',
  styleUrls: ['./admin-form.component.scss']
})
export class AdminFormComponent implements OnInit{



  @Input() adminPassed: Admin | null | undefined;

  adminForm : FormGroup = Object.create(null);
  adminListFather: adminListComponent | undefined;
  admin : Admin | undefined ;

  constructor(
    private AdminService : AdminService,
    private formBuilder: FormBuilder,
    @Host() adminListFather: adminListComponent
  ){
    this.adminListFather = adminListFather;
  }

  ngOnInit(): void {
    this.adminForm = this.formBuilder.group({
      email: new FormControl("" , Validators.required),
      motDePass: new FormControl("", Validators.required)
    });

    if(this.adminPassed!=null)
    {
      this.display(this.adminPassed);
    }
    else{
      this.display(new Admin());
    }

    
  }

  save()
  {
    console.log(this.adminForm.value)
    let adminToAdd : Admin = {...this.admin,...this.adminForm.value};
    console.log(adminToAdd)
    if(adminToAdd.uuid!='')
    {
      this.AdminService.updateAdmin(adminToAdd).subscribe(
        (adminAdded)=>
        {
          Swal.fire({
                          title: 'Success!',
                          text: 'Your operation was successful!',
                          icon: 'success',
                          confirmButtonText: 'OK'
                        });
          this.adminListFather?.closeModal();
          this.adminListFather?.initList();
        }
      )
    }
    else
    {
      this.AdminService.newAdmin(adminToAdd).subscribe(
        (adminAdded)=>
        {
          Swal.fire({
                          title: 'Success!',
                          text: 'Your operation was successful!',
                          icon: 'success',
                          confirmButtonText: 'OK'
                        });
          this.adminListFather?.closeModal();
          this.adminListFather?.initList();
        }
      )
    }
    
  }

  display(adminToDisplay : Admin){
    this.admin = adminToDisplay
    this.adminForm.controls['email'].setValue(adminToDisplay.email);
    this.adminForm.controls['motDePass'].setValue(adminToDisplay.motDePass);
  }

}


