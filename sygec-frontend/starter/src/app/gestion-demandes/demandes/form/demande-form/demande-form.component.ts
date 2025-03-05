import { Component, Host, Input, OnInit } from '@angular/core';
import { DemandeConge } from '../../../../models/demande-conge';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { DemandeListComponent } from '../../list/demande-list/demande-list.component';
import { ServiceeService } from '../../../../services/servicee.service';
import { Servicee } from '../../../../models/servicee';
import { Beneficiaire } from '../../../../models/beneficiaire';
import { BeneficiaireService } from '../../../../services/beneficiaire.service';
import { DemandeCongeService } from '../../../../services/demande-conge.service';
import { PosteService } from '../../../../services/poste.service';
import Swal from 'sweetalert2';
import { TypeCongeService } from '../../../../services/type-conge.service';
import { Poste } from '../../../../models/poste';
import { StatutDemande } from '../../../../models/statut-demande';

@Component({
  selector: 'app-demande-form',
  templateUrl: './demande-form.component.html',
  styleUrls: ['./demande-form.component.css']
})
export class DemandeFormComponent implements OnInit {

  @Input() innerDemande!: DemandeConge;
  @Input() demandePassed: DemandeConge | null | undefined;
  demandeForm: FormGroup = Object.create(null);
  demandeListFather: DemandeListComponent | undefined;
  demande: DemandeConge = new DemandeConge(); // Initialisation
  listPoste: Poste [] = [];
  listService: Servicee [] = [];
  dataBeneficiaire: any;
  dataService: any;
  // dataPoste: any;
  dataTypeConge: any;
  isEnable: boolean = false;
  isEdit: boolean = false;
  futureCode: string = "";
  loading:boolean=false;
  demandeConge: DemandeConge = new DemandeConge(); // Initialisation

  constructor(
    private demandeService: DemandeCongeService,
    private posteService: PosteService,
    private serviceService: ServiceeService,
    private beneficiaireService: BeneficiaireService,
    private typeCongeServive: TypeCongeService,
    private formBuilder: FormBuilder,
    @Host() demandeListFather: DemandeListComponent
  ) {
    this.demandeListFather = demandeListFather;
  }

  ngOnInit(): void {
    this.demandeForm = this.formBuilder.group({
      code: new FormControl("", Validators.required),
      dateDebut: new FormControl("", Validators.required),
      dateFin: new FormControl("", Validators.required),
      motif: new FormControl("", Validators.required),
      beneficiaire_uuid: new FormControl("", Validators.required),
      libelleBeneficiaire: new FormControl("", Validators.required),
      poste_uuid: new FormControl("", Validators.required),
      libellePoste: new FormControl("", Validators.required),
      servicee_uuid: new FormControl("", Validators.required),
      libelleServicee: new FormControl("", Validators.required),
      typeConge_uuid: new FormControl("", Validators.required),
      libelleTypeConge: new FormControl("", Validators.required),
      nombreJours: new FormControl("", Validators.required)
    });

    this.demandeForm.get('code')?.disable();

    this.demandeService.getCodeDemandeConge().subscribe(
      (futurCode) => {
        this.futureCode = futurCode.value;
        if (this.innerDemande != null) {
          this.isEdit = true;
          this.isEnable = false;
          this.demandeForm.disable();
          this.demandeConge = this.innerDemande;
          this.futureCode = this.demandeConge.code;
          this.demandeForm.get('code')?.disable();
          this.display(this.demandeConge);
        } else {
          this.isEdit = false;
          this.isEnable = true;
          this.demandeForm.enable();
          this.demandeConge = new DemandeConge();
          this.demandeForm.get('code')?.disable();
          this.display(this.demandeConge);
        }
      }
    );

    if (this.demandePassed != null) {
      this.display(this.demandePassed);
    } else {
      this.display(new DemandeConge());
    }
    
    this.getService();
    this.getBeneficiaire();
    // this.getPoste();
    this.getTypeConge();
  }



  save() {
    if (!this.isEdit) {
      this.demandeConge.code = this.futureCode;
    }
  
    // Récupération des valeurs du formulaire
    let demandeToAdd: DemandeConge = {...this.demande, ...this.demandeForm.value};
  
    // Initialisation des statuts
    demandeToAdd.valider = false;
    demandeToAdd.rejetter = false;
    demandeToAdd.annuler = false;
    demandeToAdd.statut = StatutDemande.EN_ATTENTE;
  
    // Validation des dates
    const dateDebut = new Date(demandeToAdd.dateDebut);
    const dateFin = new Date(demandeToAdd.dateFin);
    const today = new Date();
  
    // Vérifier que la date de fin est après la date de début
    if (dateFin < dateDebut) {
      Swal.fire({
        title: 'Erreur!',
        text: 'La date de fin doit être postérieure à la date de début',
        icon: 'error',
        confirmButtonText: 'OK'
      });
      return;
    }
  
    // Vérifier le délai minimum avant la date de début (7 jours ici)
    const DELAI_MIN_DEMANDE = 7; // Règle mise à jour : la demande doit être faite 7 jours avant le début
    const joursAvantConge = Math.floor((dateDebut.getTime() - today.getTime()) / (1000 * 60 * 60 * 24));
    if (joursAvantConge < DELAI_MIN_DEMANDE) {
      Swal.fire({
        title: 'Erreur!',
        text: `La demande doit être faite au moins ${DELAI_MIN_DEMANDE} jours avant la date de début du congé`,
        icon: 'error',
        confirmButtonText: 'OK'
      });
      return;
    }
  
    // Calculer la durée du congé (simplifié, le calcul précis se fera côté backend)
    const DUREE_MIN_CONGE = 1; // À ajuster selon la constante du backend
    const DUREE_MAX_CONGE = 30; // À ajuster selon la constante du backend
  
    // Afficher un indicateur de chargement
    this.loading = true;
  
    // Envoyer la demande au serveur
    if (demandeToAdd.uuid && demandeToAdd.uuid !== '') {
      // Mise à jour d'une demande existante
      this.demandeService.updateDemandeConge(demandeToAdd).subscribe(
        (demandeUpdated) => {
          this.loading = false;
          Swal.fire({
            title: 'Succès!',
            text: 'Votre demande de congé a été mise à jour avec succès!',
            icon: 'success',
            confirmButtonText: 'OK'
          });
          this.demandeListFather?.closeModal();
          this.demandeListFather?.initList();
        },
        (error) => {
          this.loading = false;
          Swal.fire({
            title: 'Erreur!',
            text: error.error?.message || 'Une erreur s\'est produite lors de la mise à jour de la demande',
            icon: 'error',
            confirmButtonText: 'OK'
          });
        }
      );
    } else {
      // Création d'une nouvelle demande
      this.demandeService.newDemandeConge(demandeToAdd).subscribe(
        (demandeAdded) => {
          this.loading = false;
          Swal.fire({
            title: 'Succès!',
            text: 'Votre demande de congé a été enregistrée avec succès!',
            icon: 'success',
            confirmButtonText: 'OK'
          });
          this.demandeListFather?.closeModal();
          this.demandeListFather?.initList();
        },
        (error) => {
          this.loading = false;
          Swal.fire({
            title: 'Erreur!',
            text: error.error?.message || 'Désolé veuillez verifier si vous avez un solde conge pour l année là, oubien si vous n avez pas déjà une demande en cours, veuillez patienter en attendant la ' +
            'validation du CHEF Responsable des Congés',
            icon: 'error',
            confirmButtonText: 'OK'
          });
        }
      );
    }
  }
  

  display(demandeToDisplay: DemandeConge) {
    this.demande = demandeToDisplay;
    this.demandeForm.controls['code'].setValue(demandeToDisplay.code);
    this.demandeForm.controls['beneficiaire_uuid'].setValue(demandeToDisplay.beneficiaire_uuid);
    this.demandeForm.controls['typeConge_uuid'].setValue(demandeToDisplay.typeConge_uuid);
    this.demandeForm.controls['servicee_uuid'].setValue(demandeToDisplay.servicee_uuid);
    this.demandeForm.controls['poste_uuid'].setValue(demandeToDisplay.poste_uuid);
    this.demandeForm.controls['dateDebut'].setValue(demandeToDisplay.dateDebut);
    this.demandeForm.controls['dateFin'].setValue(demandeToDisplay.dateFin);
    this.demandeForm.controls['nombreJours'].setValue(demandeToDisplay.nombreJours);
  }

  chargerPoste(uuid:string){
    console.log(uuid)
    this.listPoste=[]
    this.posteService.getAllPosteByServicee(uuid).subscribe(
      (list:Poste[])=>{
        this.listPoste=list
        console.log(list)
      }
    )
  }

  getService() {
    this.serviceService.getAllServicee().subscribe((list:Servicee[]) => {
     this.listService = list
    });
  }

  getBeneficiaire() {
    this.beneficiaireService.getAllBeneficiaire().subscribe((res: any) => {
      this.dataBeneficiaire = res;
    });
  }

  // getPoste() {
  //   this.posteService.getAllPoste().subscribe((res: any) => {
  //     this.dataPoste = res;
  //   });
  // }

  getTypeConge() {
    this.typeCongeServive.getAllTypeConge().subscribe((res: any) => {
      this.dataTypeConge = res;
    });
  }
}