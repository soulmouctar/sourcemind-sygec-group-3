import { Component, Host, Input, OnInit } from '@angular/core';
import { Beneficiaire } from '../../../../models/beneficiaire';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

import { ServiceeService } from '../../../../services/servicee.service';
import { BeneficiaireService } from '../../../../services/beneficiaire.service';
import Swal from 'sweetalert2';
import { beneficiaireListComponent } from '../../list/beneficiaire-list/beneficiaire-list.component';
import { FichierService } from '../../../../services/fichier.service';

@Component({
  selector: 'app-beneficiaire-form',
  templateUrl: './beneficiaire-form.component.html',
  styleUrls: ['./beneficiaire-form.component.scss']
})
export class BeneficiaireFormComponent implements OnInit{



  @Input() beneficiairePassed: Beneficiaire | null | undefined;

  beneficiaireForm : FormGroup = Object.create(null);
  beneficiaireListFather: beneficiaireListComponent | undefined;
  beneficiaire : Beneficiaire | undefined ;
  dataService : any;
    link = ""
    image!: string;

  constructor(
    private ServiceService : ServiceeService,
    private BeneficiaireService : BeneficiaireService,
    private formBuilder: FormBuilder,
    private fichierService : FichierService,

    @Host() beneficiaireListFather: beneficiaireListComponent
  ){
    this.beneficiaireListFather = beneficiaireListFather;
  }

  ngOnInit(): void {
    this.beneficiaireForm = this.formBuilder.group({
      service_uuid: new FormControl("" , Validators.required),
      nom: new FormControl("" , Validators.required),
      prenom: new FormControl("", Validators.required),
      grade: new FormControl("" , Validators.required),
      email: new FormControl("", Validators.required),
      // motDePass: new FormControl("" , Validators.required),
      photo: new FormControl("", Validators.required),
      phone: new FormControl("", Validators.required),
      role: new FormControl("", Validators.required),
      matricule: new FormControl("", Validators.required),
      imgArcticle : new FormControl("", Validators.required)

    });

    if(this.beneficiairePassed!=null)
    {
      this.display(this.beneficiairePassed);
    }
    else{
      this.display(new Beneficiaire());
    }
    this.getService()
    
  }

  save()
  {
    console.log(this.beneficiaireForm.value)
    let beneficiaireToAdd : Beneficiaire = {...this.beneficiaire,...this.beneficiaireForm.value};
    console.log(beneficiaireToAdd)
    if(beneficiaireToAdd.uuid!='')
    {
      this.BeneficiaireService.updateBeneficiaire(beneficiaireToAdd).subscribe(
        (detaillivraisonAdded)=>
        {
           Swal.fire({
                           title: 'Success!',
                           text: 'Your operation was successful!',
                           icon: 'success',
                           confirmButtonText: 'OK'
                         });
          this.beneficiaireListFather?.closeModal();
          this.beneficiaireListFather?.initList();
        }
      )
    }
    else
    {
      console.log("hello")
      this.BeneficiaireService.newBeneficiaire(beneficiaireToAdd).subscribe(
        (beneficiaireAdded)=>
        {
          Swal.fire({
                          title: 'Success!',
                          text: 'Your operation was successful!',
                          icon: 'success',
                          confirmButtonText: 'OK'
                        });
          this.beneficiaireListFather?.closeModal();
          this.beneficiaireListFather?.initList();
        }
      )
    }
    
  }

  display(beneficiaireToDisplay : Beneficiaire){
    this.beneficiaire = beneficiaireToDisplay
    this.beneficiaireForm.controls['service_uuid'].setValue(beneficiaireToDisplay.service_uuid);
    this.beneficiaireForm.controls['nom'].setValue(beneficiaireToDisplay.nom);
    this.beneficiaireForm.controls['prenom'].setValue(beneficiaireToDisplay.prenom);
    this.beneficiaireForm.controls['grade'].setValue(beneficiaireToDisplay.grade);
    this.beneficiaireForm.controls['email'].setValue(beneficiaireToDisplay.email);
    this.beneficiaireForm.controls['photo'].setValue(beneficiaireToDisplay.photo);
    this.beneficiaireForm.controls['imgArcticle'].setValue(beneficiaireToDisplay.imgArcticle);
  }
  upLoadFile(event: any) {
    if (event.target.files && event.target.files[0]) {
      console.log(event.target.files[0])
      this.fichierService.SaveFile(event.target.files[0]).subscribe((data:any) => {
        console.log(data)
        this.getImageAsDataUrl(data.uuid);
        this.link = data.uuid
        this.beneficiaireForm.patchValue({
          uuidPhoto: data.uuid,
        });
      });
    }
  }

  getImageAsDataUrl(id: string) {
    this.fichierService.getImgAsDataUrl(id).subscribe(
      (data: any) => {
      console.log(data)
      if (data != null) {
        if(data.bytes != null && data.bytes !=''){
          this.image = "data:image/jpeg;base64," + data.bytes;
        }
        else{
          this.image = "data:image/jpeg;base64," + data.data;
        }
      }
    });
  }

  getService(){
    this.ServiceService.getAllServicee().subscribe((res: any) => {
      this.dataService = res;
    })
  }

}
