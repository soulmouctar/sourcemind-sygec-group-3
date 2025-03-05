import { Component, Host, Input, OnInit } from '@angular/core';
import { TypeConge } from '../../../../models/type-conge';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { TypeCongeListComponent } from '../../list/type-conge-list/type-conge-list.component';
import { TypeCongeService } from '../../../../services/type-conge.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-type-conge-form',
  templateUrl: './type-conge-form.component.html',
  styleUrls: ['./type-conge-form.component.css']
})
export class TypeCongeFormComponent implements OnInit{


  @Input() typeCongePassed: TypeConge | null | undefined;

  typeCongeForm : FormGroup = Object.create(null);
  typeCongeListFather: TypeCongeListComponent | undefined;
  typeConge : TypeConge | undefined ;
   TypeConge!: TypeConge;

  constructor(
    private typeCongeServive : TypeCongeService,
    private formBuilder: FormBuilder,

    @Host() typeCongeListFather: TypeCongeListComponent
  ){
    this.typeCongeListFather = typeCongeListFather;
  }

  ngOnInit(): void {
    this.typeCongeForm = this.formBuilder.group({
      libelle: new FormControl("" , Validators.required),
      deductible: new FormControl("" , Validators.required),
      description: new FormControl("", Validators.required)
     

    });

    if(this.typeCongePassed!=null)
    {
      this.display(this.typeCongePassed);
    }
    else{
      this.display(new TypeConge());
    }
    
  }

  save()
  {

    console.log(this.typeCongeForm.value)
    let typeCongeToAdd : TypeConge = {...this.typeConge,...this.typeCongeForm.value};
    console.log(typeCongeToAdd)
    if(typeCongeToAdd.uuid!='')
    {
      this.typeCongeServive.updateTypeConge(typeCongeToAdd).subscribe(
        (typeCongeAdded)=>
        {
           Swal.fire({
                           title: 'Success!',
                           text: 'Your operation was successful!',
                           icon: 'success',
                           confirmButtonText: 'OK'
                         });
          this.typeCongeListFather?.closeModal();
          this.typeCongeListFather?.initList();
        }
      )
    }
    else
    {
      console.log("hello")
      this.typeCongeServive.newTypeConge(typeCongeToAdd).subscribe(
        (typeCongeAdded)=>
        {
          Swal.fire({
                          title: 'Success!',
                          text: 'Your operation was successful!',
                          icon: 'success',
                          confirmButtonText: 'OK'
                        });
          this.typeCongeListFather?.closeModal();
          this.typeCongeListFather?.initList();
        }
      )
    }
    
  }

  display(typeCongeToDisplay : TypeConge){
    this.typeConge = typeCongeToDisplay
    this.typeCongeForm.controls['libelle'].setValue(typeCongeToDisplay.libelle);
    this.typeCongeForm.controls['deductible'].setValue(typeCongeToDisplay.deductible);
    this.typeCongeForm.controls['description'].setValue(typeCongeToDisplay.description);
  }

}

