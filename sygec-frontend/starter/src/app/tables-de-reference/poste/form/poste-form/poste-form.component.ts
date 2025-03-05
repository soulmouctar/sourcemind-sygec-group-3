import { Component, Host, Input, OnInit } from '@angular/core';
import { Poste } from '../../../../models/poste';
import { PosteListComponent } from '../../list/poste-list/poste-list.component';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { PosteService } from '../../../../services/poste.service';
import Swal from 'sweetalert2';
import { ServiceeService } from '../../../../services/servicee.service';

@Component({
  selector: 'app-poste-form',
  templateUrl: './poste-form.component.html',
  styleUrls: ['./poste-form.component.css']
})
export class PosteFormComponent implements OnInit{

  @Input() postePassed: Poste | null | undefined;

  posteForm : FormGroup = Object.create(null);
  posteListFather: PosteListComponent | undefined;
  poste : Poste | undefined ;
   Poste!: Poste;

   dataService:any

  constructor(
    private posteService : PosteService,
    private serviceService : ServiceeService,
    private formBuilder: FormBuilder,

    @Host() posteListFather: PosteListComponent
  ){
    this.posteListFather = posteListFather;
  }

  ngOnInit(): void {
    this.posteForm = this.formBuilder.group({
      libelle: new FormControl("" , Validators.required),
      description: new FormControl("", Validators.required),
      servicee_uuid: new FormControl("", Validators.required)
     

    });

    if(this.postePassed!=null)
    {
      this.display(this.postePassed);
    }
    else{
      this.display(new Poste());
    }
    this.getService();
    
  }

  save()
  {

    console.log(this.posteForm.value)
    let posteToAdd : Poste = {...this.poste,...this.posteForm.value};
    console.log(posteToAdd)
    if(posteToAdd.uuid!='')
    {
      this.posteService.updatePoste(posteToAdd).subscribe(
        (detaillivraisonAdded)=>
        {
           Swal.fire({
                           title: 'Success!',
                           text: 'Your operation was successful!',
                           icon: 'success',
                           confirmButtonText: 'OK'
                         });
          this.posteListFather?.closeModal();
          this.posteListFather?.initList();
        }
      )
    }
    else
    {
      console.log("hello")
      this.posteService.newPoste(posteToAdd).subscribe(
        (posteAdded)=>
        {
          Swal.fire({
                          title: 'Success!',
                          text: 'Your operation was successful!',
                          icon: 'success',
                          confirmButtonText: 'OK'
                        });
          this.posteListFather?.closeModal();
          this.posteListFather?.initList();
        }
      )
    }
    
  }

  display(posteToDisplay : Poste){
    this.poste = posteToDisplay
    this.posteForm.controls['libelle'].setValue(posteToDisplay.libelle);
    this.posteForm.controls['description'].setValue(posteToDisplay.description);
    this.posteForm.controls['servicee_uuid'].setValue(posteToDisplay.servicee_uuid);
  }

  getService(){
    this.serviceService.getAllServicee().subscribe((res: any) => {
      this.dataService = res;
    })
  }


}
