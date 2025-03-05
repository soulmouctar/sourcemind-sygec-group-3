import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ParametresConfigService } from '../../../services/parametre-config.service';
import { AuthService } from '../../../../_services/auth.service';
import { ParametreConfig } from '../../../models/parametre-config';
import { TokenStorageService } from '../../../../_services/token-storage.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-parametre-config',
  templateUrl: './parametre-config.component.html',
  styleUrls: ['./parametre-config.component.scss']
})
export class ParametreConfigComponent implements OnInit {
  parametresForm: FormGroup;
  loading = false;
  isAdmin = false;
  isSaving = false;
  errorMessage = '';
  successMessage = '';

  constructor(
    private fb: FormBuilder,
    private parametresConfigService: ParametresConfigService,
    private authService: AuthService,
    private tokenService:TokenStorageService
  ) {
    this.parametresForm = this.createForm();
  }

  ngOnInit(): void {
    this.isAdmin = this.tokenService.getRole().indexOf('ADMIN')!=-1;
    {
      this.isAdmin=true
    };
    
    // Rediriger si l'utilisateur n'est pas admin
    if (!this.isAdmin) {
      // Redirection ou blocage selon votre logique d'application
      console.error('Accès non autorisé à la page de configuration');
      return;
    }

    this.loadParametres();
  }

  /**
   * Crée le formulaire avec des validateurs pour chaque champ
   */
  createForm(): FormGroup {
    return this.fb.group({
      dureeMinConge: [1, [Validators.required, Validators.min(1), Validators.max(30)]],
      dureeMaxConge: [90, [Validators.required, Validators.min(1), Validators.max(365)]],
      delaiMinDemande: [7, [Validators.required, Validators.min(0), Validators.max(90)]],
      soldeMinRequis: [0.5, [Validators.required, Validators.min(0), Validators.max(10)]]
    });
  }

  /**
   * Charge les paramètres actuels
   */
  loadParametres(): void {
    this.loading = true;
    this.parametresConfigService.getParametres()
      .subscribe(
        (config) => {
          this.parametresForm.patchValue(config);
          this.loading = false;
        },
        (error) => {
          console.error('Erreur lors du chargement des paramètres', error);
          this.errorMessage = 'Erreur lors du chargement des paramètres';
          this.loading = false;
        }
      );
  }

  /**
   * Enregistre les modifications des paramètres
   */
  // onSubmit(): void {
  //   if (this.parametresForm.invalid) {
  //     this.markFormGroupTouched(this.parametresForm);
  //     return;
  //   }

  //   this.isSaving = true;
  //   this.errorMessage = '';
  //   this.successMessage = '';

  //   const formValue = this.parametresForm.value as ParametreConfig;

  //   // Vérifier que la durée minimale est inférieure à la durée maximale
  //   if (formValue.dureeMinConge > formValue.dureeMaxConge) {
  //     this.errorMessage = 'La durée minimale ne peut pas être supérieure à la durée maximale';
  //     this.isSaving = false;
  //     return;
  //   }
  //   console.log(formValue)
  //   this.parametresConfigService.updateParametres(formValue)
  
  //     .subscribe(
  //       () => {
  //         this.successMessage = 'Paramètres enregistrés avec succès';
  //         this.isSaving = false;
  //       },
  //       (error) => {
  //         console.error('Erreur lors de l\'enregistrement des paramètres', error);
  //         this.errorMessage = 'Erreur lors de l\'enregistrement des paramètres';
  //         this.isSaving = false;
  //       }
  //     );
  // }

  onSubmit(): void {
    if (this.parametresForm.invalid) {
      this.markFormGroupTouched(this.parametresForm);
      return;
    }
  
    this.isSaving = true;
    this.errorMessage = '';
    this.successMessage = '';
  
    const formValue = this.parametresForm.value as ParametreConfig;
  
    // Vérifier que la durée minimale est inférieure à la durée maximale
    if (formValue.dureeMinConge > formValue.dureeMaxConge) {
      this.errorMessage = 'La durée minimale ne peut pas être supérieure à la durée maximale';
      this.isSaving = false;
      return;
    }
  
    console.log('Envoi des paramètres:', formValue);
    
    this.parametresConfigService.updateParametres(formValue)
      .subscribe(
        (response) => {
          console.log('Réponse du serveur:', response);
          this.successMessage = 'Paramètres enregistrés avec succès';
          this.isSaving = false;
          Swal.fire(
            'Enregistrer !',
            'L\'élément a été enregistré avec succès.',
            'success'
          );
          
          // Mettre à jour le formulaire avec les valeurs retournées par l'API
          if (response) {
            this.updateFormWithResponse(response);
          }
        },
        (error) => {
          console.error('Erreur lors de l\'enregistrement des paramètres', error);
          Swal.fire(
            'Erreur !',
            'Erreur lors de l\'enregistrement des paramètres', error
          );
          // Message d'erreur plus spécifique si disponible
          if (error.error && error.error.message) {
            this.errorMessage = error.error.message;
          } else {
            this.errorMessage = 'Erreur lors de l\'enregistrement des paramètres. Veuillez réessayer.';
            Swal.fire(
              'Erreur !',
              'Erreur lors de l\'enregistrement des paramètres. Veuillez réessayer.'
            );
          }
          
          this.isSaving = false;
        },
        () => {
          // Compléter - exécuté que la requête réussisse ou échoue
          this.isSaving = false;
        }
      );
  }
  
  // Nouvelle méthode pour mettre à jour le formulaire avec la réponse
  private updateFormWithResponse(response: ParametreConfig): void {
    this.parametresForm.patchValue({
      dureeMinConge: response.dureeMinConge,
      dureeMaxConge: response.dureeMaxConge,
      delaiMinDemande: response.delaiMinDemande,
      soldeMinRequis: response.soldeMinRequis
    });
  }
  /**
   * Réinitialise les paramètres aux valeurs par défaut
   */
  // resetToDefault(): void {
  //   if (confirm('Êtes-vous sûr de vouloir réinitialiser tous les paramètres aux valeurs par défaut ?')) {
  //     this.isSaving = true;
  //     this.errorMessage = '';
  //     this.successMessage = '';

  //     this.parametresConfigService.resetParametres()
  //       .subscribe(
  //         (defaultConfig) => {
  //           this.parametresForm.patchValue(defaultConfig);
  //           this.successMessage = 'Paramètres réinitialisés avec succès';
  //           this.isSaving = false;
  //         },
  //         (error) => {
  //           console.error('Erreur lors de la réinitialisation des paramètres', error);
  //           this.errorMessage = 'Erreur lors de la réinitialisation des paramètres';
  //           this.isSaving = false;
  //         }
  //       );
  //   }
  // }

  resetToDefault(): void {
    Swal.fire({
      title: 'Réinitialiser les paramètres ?',
      text: "Tous les paramètres seront réinitialisés aux valeurs par défaut. Cette action est irréversible.",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui, réinitialiser',
      cancelButtonText: 'Non, annuler',
    }).then((result) => {
      if (result.isConfirmed) {
        this.isSaving = true;
        this.errorMessage = '';
        this.successMessage = '';
  
        this.parametresConfigService.resetParametres()
          .subscribe(
            (defaultConfig) => {
              this.parametresForm.patchValue(defaultConfig);
              this.isSaving = false;
              
              Swal.fire(
                'Réinitialisation réussie !',
                'Les paramètres ont été réinitialisés avec succès.',
                'success'
              );
            },
            (error) => {
              console.error('Erreur lors de la réinitialisation des paramètres', error);
              this.isSaving = false;
              
              Swal.fire(
                'Erreur',
                'Une erreur est survenue lors de la réinitialisation des paramètres.',
                'error'
              );
            }
          );
      } else if (result.isDismissed) {
        Swal.fire(
          'Annulé',
          'Les paramètres n\'ont pas été modifiés.',
          'info'
        );
      }
    });
  }

  /**
   * Marque tous les champs du formulaire comme touchés pour afficher les erreurs
   */
  markFormGroupTouched(formGroup: FormGroup) {
    Object.values(formGroup.controls).forEach(control => {
      control.markAsTouched();
      if (control instanceof FormGroup) {
        this.markFormGroupTouched(control);
      }
    });
  }
}