import { Component, Host, Input, OnInit } from '@angular/core';
import { SoldeConge } from '../../../../models/solde-conge';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { SoldeCongeListComponent } from '../../list/solde-conge-list/solde-conge-list.component';
import { SoldeCongeService } from '../../../../services/solde-conge.service';
import { BeneficiaireService } from '../../../../services/beneficiaire.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-solde-conge-form',
  templateUrl: './solde-conge-form.component.html',
  styleUrls: ['./solde-conge-form.component.css']
})
export class SoldeCongeFormComponent implements OnInit{



  @Input() soldeCongePassed: SoldeConge | null | undefined;

  soldeCongeForm : FormGroup = Object.create(null);
  soldeCongeListFather: SoldeCongeListComponent | undefined;
  soldeConge : SoldeConge | undefined ;
  dataBeneficiaire : any;
   SoldeConge!: SoldeConge;

  constructor(
    private soldeCongeService : SoldeCongeService,
    private beneficiaireService : BeneficiaireService,
    private formBuilder: FormBuilder,

    @Host() soldeCongeListFather: SoldeCongeListComponent
  ){
    this.soldeCongeListFather = soldeCongeListFather;
  }

  ngOnInit(): void {
    this.soldeCongeForm = this.formBuilder.group({
      soldeAnnuel: new FormControl("" , Validators.required),
      soldeRestandt: new FormControl("" , Validators.required),
      annee: new FormControl("", Validators.required),
      beneficiaire_uuid: new FormControl("", Validators.required)
     

    });

    if(this.soldeCongePassed!=null)
    {
      this.display(this.soldeCongePassed);
    }
    else{
      this.display(new SoldeConge());
    }
    this.getBeneficiaire()
  
    
  }

  save()
  {

    console.log(this.soldeCongeForm.value)
    let soldeCongeToAdd : SoldeConge = {...this.soldeConge,...this.soldeCongeForm.value};
    console.log(soldeCongeToAdd)
    if(soldeCongeToAdd.uuid!='')
    {
      this.soldeCongeService.updateSoldeConge(soldeCongeToAdd).subscribe(
        (detaillivraisonAdded)=>
        {
           Swal.fire({
                           title: 'Success!',
                           text: 'Your operation was successful!',
                           icon: 'success',
                           confirmButtonText: 'OK'
                         });
          this.soldeCongeListFather?.closeModal();
          this.soldeCongeListFather?.initList();
        }
      )
    }
    else
    {
      console.log("hello")
      this.soldeCongeService.newSoldeConge(soldeCongeToAdd).subscribe(
        (soldeCongeAdded)=>
        {
          Swal.fire({
                          title: 'Success!',
                          text: 'Your operation was successful!',
                          icon: 'success',
                          confirmButtonText: 'OK'
                        });
          this.soldeCongeListFather?.closeModal();
          this.soldeCongeListFather?.initList();
        }
      )
    }
    
  }

  display(soldeCongeToDisplay : SoldeConge){
    this.soldeConge = soldeCongeToDisplay
    this.soldeCongeForm.controls['soldeAnnuel'].setValue(soldeCongeToDisplay.soldeAnnuel);
    this.soldeCongeForm.controls['soldeRestandt'].setValue(soldeCongeToDisplay.soldeRestandt);
    this.soldeCongeForm.controls['annee'].setValue(soldeCongeToDisplay.annee);
    this.soldeCongeForm.controls['beneficiaire_uuid'].setValue(soldeCongeToDisplay.beneficiaire_uuid);
  }


  getBeneficiaire(){
    this.beneficiaireService.getAllBeneficiaire().subscribe((res: any) => {
      this.dataBeneficiaire = res;
    })
  }
}
