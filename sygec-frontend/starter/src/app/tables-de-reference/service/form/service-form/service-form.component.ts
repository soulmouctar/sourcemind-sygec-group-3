import { Component, Host, Input, OnInit } from '@angular/core';
import { Servicee } from '../../../../models/servicee';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ServiceListComponent } from '../../list/service-list/service-list.component';
import { ServiceeService } from '../../../../services/servicee.service';
import { PosteService } from '../../../../services/poste.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-service-form',
  templateUrl: './service-form.component.html',
  styleUrls: ['./service-form.component.css']
})
export class serviceFormComponent implements OnInit{

  @Input() servicePassed: Servicee | null | undefined;

  serviceForm : FormGroup = Object.create(null);
  serviceListFather: ServiceListComponent | undefined;
  service : Servicee | undefined ;
  dataPoste : any;
   Servicee!: Servicee;

  constructor(
    private serviceService : ServiceeService,
    private posteService : PosteService,
    private formBuilder: FormBuilder,

    @Host() serviceListFather: ServiceListComponent
  ){
    this.serviceListFather = serviceListFather;
  }

  ngOnInit(): void {
    this.serviceForm = this.formBuilder.group({
    libelle: new FormControl("" , Validators.required),
      description: new FormControl("" , Validators.required)
     

    });

   

    if(this.servicePassed!=null)
    {
      this.display(this.servicePassed);
    }
    else{
      this.display(new Servicee());
    }
    
  }

  save()
  {

    console.log(this.serviceForm.value)
    let serviceToAdd : Servicee = {...this.service,...this.serviceForm.value};
    console.log(serviceToAdd)
    if(serviceToAdd.uuid!='')
    {
      this.serviceService.updateServicee(serviceToAdd).subscribe(
        (detaillivraisonAdded)=>
        {
           Swal.fire({
                           title: 'Success!',
                           text: 'Your operation was successful!',
                           icon: 'success',
                           confirmButtonText: 'OK'
                         });
          this.serviceListFather?.closeModal();
          this.serviceListFather?.initList();
        }
      )
    }
    else
    {
      console.log("hello")
      this.serviceService.newServicee(serviceToAdd).subscribe(
        (serviceAdded)=>
        {
          Swal.fire({
                          title: 'Success!',
                          text: 'Your operation was successful!',
                          icon: 'success',
                          confirmButtonText: 'OK'
                        });
          this.serviceListFather?.closeModal();
          this.serviceListFather?.initList();
        }
      )
    }
    
  }

  display(serviceToDisplay : Servicee){
    this.service = serviceToDisplay
    this.serviceForm.controls['libelle'].setValue(serviceToDisplay.libelle);
    this.serviceForm.controls['description'].setValue(serviceToDisplay.description);
  }




}

