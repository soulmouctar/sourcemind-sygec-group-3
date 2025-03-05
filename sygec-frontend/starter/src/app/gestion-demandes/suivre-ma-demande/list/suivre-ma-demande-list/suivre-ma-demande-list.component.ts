import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { DemandeConge } from '../../../../models/demande-conge';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DemandeCongeService } from '../../../../services/demande-conge.service';
import { StatutDemande } from '../../../../models/statut-demande';
import { SoldeConge } from '../../../../models/solde-conge';
import { SoldeCongeService } from '../../../../services/solde-conge.service';

@Component({
  selector: 'app-suivre-ma-demande-list',
  templateUrl: './suivre-ma-demande-list.component.html',
  styleUrls: ['./suivre-ma-demande-list.component.scss']
})
export class SuivreMaDemandeListComponent implements OnInit {
  soldeCongeParam: SoldeConge | null = new SoldeConge;
  demandeParam: DemandeConge | null = new DemandeConge;
  StatutDemande = StatutDemande;
  demandecongeList: DemandeConge[] = [];
  
  viewMode: 'table' | 'cards' = 'table';
  filteredListDbeneficiaire: DemandeConge[] = []; // For search results
  filteredListDSoldeConge: SoldeConge[] = []; 
     // Pagination parameters
     page = 1;
     pageSize = 10;
     collectionSize = 0;
     
     // Search parameter
     searchTerm: string = '';

  constructor(
      private modal : NgbModal,
      private demandeCongeService : DemandeCongeService,
      private soldeCongeService : SoldeCongeService
     
    ){

  }
  
  ngOnInit(): void {
    // Chargez d'abord les soldes, puis les demandes
    this.soldeCongeService.getAllSoldeConge().subscribe(
      (soldes: SoldeConge[]) => {
        console.log('Soldes chargés:', soldes);
        this.filteredListDSoldeConge = soldes;
        
        // Puis chargez les demandes
        this.initList();
      },
      error => {
        console.error('Erreur lors du chargement des soldes:', error);
        // Charger les demandes même en cas d'erreur
        this.initList();
      }
    );
    
    // Détection initiale pour mobile
    this.viewMode = window.innerWidth < 768 ? 'cards' : 'table';
    
    // Écouteur de redimensionnement...
  }

  initList(){
    this.demandeCongeService.getAllDemandeConge().subscribe(
      (list: DemandeConge[])=>{
        console.log(list)
        this.demandecongeList=list;
        this.filteredListDbeneficiaire = list;
        this.collectionSize = list.length;
        this.applyFilters();
      }
    ); 
  }


// Ajouter cette méthode dans votre composant
// Modifiez votre getSoldeCongeForDemande avec des logs de débogage
// getSoldeCongeForDemande(demandeConge: any): any {
//   console.log('demandeConge:', demandeConge);
//   console.log('filteredListDSoldeConge:', this.filteredListDSoldeConge);
  
//   // Si le tableau est vide, retournez un nouveau solde
//   if (!this.filteredListDSoldeConge || this.filteredListDSoldeConge.length === 0) {
//     console.log('Aucun solde disponible, création d\'un nouveau solde');
//     const newSolde = new SoldeConge();
//     // Associez éventuellement des données de la demande au nouveau solde
//     if (demandeConge) {
//       newSolde.beneficiaire_uuid = demandeConge.idBeneficiaire;
//       // Autres propriétés à initialiser...
//     }
//     return newSolde;
//   }
  
//   // Tentative de correspondance
//  // Essayez différentes propriétés pour la correspondance
// const solde = this.filteredListDSoldeConge.find(solde => {
//   return (
//     // Essayer différentes combinaisons de champs
//     solde.beneficiaire_uuid === demandeConge.idBeneficiaire ||
//     solde.beneficiaire_uuid === demandeConge.beneficiaire_uuid 
//   );
// });
  
//   if (solde) {
//     console.log('Solde trouvé:', solde);
//     return solde;
//   } else {
//     console.log('Aucun solde trouvé pour cette demande');
//     const newSolde = new SoldeConge();
//     // Associez les données de la demande au nouveau solde
//     if (demandeConge) {
//       newSolde.beneficiaire_uuid = demandeConge.idBeneficiaire;
//       // Autres propriétés à initialiser...
//     }
//     return newSolde;
//   }
// }

getSoldeCongeForDemande(demandeConge: any): any {
  // Vérification des entrées avec journalisation détaillée
  console.log('Demande:', demandeConge);
  console.log('Liste des soldes:', this.filteredListDSoldeConge);
  
  // Si pas de demande ou pas de soldes disponibles
  if (!demandeConge || !this.filteredListDSoldeConge || this.filteredListDSoldeConge.length === 0) {
    console.log('Données insuffisantes pour trouver un solde');
    return this.createDefaultSolde(demandeConge);
  }
  
  // Extraire l'ID du bénéficiaire et l'année de la demande
  const beneficiaireId = demandeConge.beneficiaire_uuid || 
                         demandeConge.idBeneficiaire || 
                         (demandeConge.beneficiaire ? demandeConge.beneficiaire.uuid : null);
  
  console.log('ID du bénéficiaire recherché:', beneficiaireId);
  
  if (!beneficiaireId) {
    console.log('Aucun ID de bénéficiaire trouvé dans la demande');
    return this.createDefaultSolde(demandeConge);
  }
  
  // Extraire l'année de la date de début (si disponible)
  let annee: number | null = null;
  if (demandeConge.dateDebut) {
    try {
      // Gérer différents formats de date possible
      const dateStr = demandeConge.dateDebut;
      // Si la date est au format YYYY-MM-DD
      if (dateStr.includes('-')) {
        annee = parseInt(dateStr.split('-')[0], 10);
      }
    } catch (e) {
      console.error('Erreur lors de l\'extraction de l\'année:', e);
    }
  }
  
  console.log('Année extraite de la demande:', annee);
  
  // Recherche avec correspondance exacte
  for (const solde of this.filteredListDSoldeConge) {
    // Log de débogage pour chaque solde comparé
    console.log('Comparaison avec solde:', solde);
    console.log('Solde beneficiaire_uuid:', solde.beneficiaire_uuid, 'vs', beneficiaireId);
    
    // Vérifier la correspondance d'ID
    const idMatch = solde.beneficiaire_uuid === beneficiaireId;
    
    // Vérifier la correspondance d'année si elle est disponible
    const yearMatch = !annee || !solde.annee || solde.annee === annee;
    
    if (idMatch && yearMatch) {
      console.log('Correspondance trouvée!');
      return solde;
    }
  }
  
  // Si aucune correspondance n'est trouvée, créer un solde par défaut
  console.log('Aucun solde correspondant trouvé, création d\'un solde par défaut');
  return this.createDefaultSolde(demandeConge, annee);
}

// Méthode utilitaire pour créer un solde par défaut
private createDefaultSolde(demandeConge: any, annee?: number | null): SoldeConge {
  const newSolde = new SoldeConge();
  
  // Définir l'ID du bénéficiaire
  if (demandeConge) {
    newSolde.beneficiaire_uuid = 
      demandeConge.beneficiaire_uuid || 
      demandeConge.idBeneficiaire || 
      (demandeConge.beneficiaire ? demandeConge.beneficiaire.uuid : null);
  }
  
  // Définir l'année
  newSolde.annee = annee || new Date().getFullYear();
  
  // Définir les valeurs par défaut
  newSolde.soldeAnnuel = 30;
  newSolde.soldeRestandt = 30;
  
  console.log('Nouveau solde créé:', newSolde);
  return newSolde;
}



// Méthode utilitaire pour créer un nouveau solde
private createNewSolde(demandeConge: any, annee: number): any {
  const newSolde = new SoldeConge();
  newSolde.beneficiaire_uuid = demandeConge.beneficiaire_uuid || demandeConge.idBeneficiaire;
  newSolde.annee = annee;
  newSolde.soldeAnnuel = 30; // Valeur par défaut, à ajuster selon vos besoins
  newSolde.soldeRestandt = 30; // Valeur par défaut, à ajuster selon vos besoins
  return newSolde;
}

// Dans initList() ou une nouvelle méthode
loadSoldeCongeData() {
  // Remplacez par votre service et méthode appropriés
  this.soldeCongeService.getAllSoldeConge().subscribe(
    (list: SoldeConge[]) => {
      console.log('Soldes chargés:', list);
      this.filteredListDSoldeConge = list;
    },
    error => {
      console.error('Erreur lors du chargement des soldes:', error);
    }
  );
}


  validerchef() {
    this.demandeCongeService.getAllDemandeCongefindAllIsNotValider().subscribe(
      (list: DemandeConge[])=>{
        console.log(list)
        list.forEach((element: DemandeConge) => {
          if (element.statut === StatutDemande.EN_ATTENTE) {
            element.valider =true
            element.rejetter = false
            element.annuler = false
            this.demandeCongeService.updateDemandeConge(element).subscribe(
              (demandecongeUpdated) => {
                console.log(demandecongeUpdated)
                Swal.fire({
                  title: 'Success!',
                  text: 'Your operation was successful!',
                  icon: 'success',
                  confirmButtonText: 'OK'
                });
                this.initList();
                this.closeModal();

              }

            )
          }
        });

      }


    )

  };

  rejetterChef() {
    this.demandeCongeService.getAllDemandeConge().subscribe(
      (list: DemandeConge[])=>{
        console.log(list)
        list.forEach((element: DemandeConge) => {
          if (element.statut === StatutDemande.EN_ATTENTE) {
            element.valider = false
            element.rejetter = true
            element.annuler = false
            this.demandeCongeService.updateDemandeConge(element).subscribe(
              (demandecongeUpdated) => {
                console.log(demandecongeUpdated)
                Swal.fire({
                  title: 'Success!',
                  text: 'Your operation was successful!',
                  icon: 'success',
                  confirmButtonText: 'OK'
                });
                this.initList();
                this.closeModal();

              }

            )
          }
        });

      }


    )

  };

  annulerchef() {
    this.demandeCongeService.getAllDemandeConge().subscribe(
      (list: DemandeConge[])=>{
        console.log(list)
        list.forEach((element: DemandeConge) => {
          if (element.statut === StatutDemande.EN_ATTENTE) {
            element.valider = false
            element.rejetter = false
            element.annuler = true
            this.demandeCongeService.updateDemandeConge(element).subscribe(
              (demandecongeUpdated) => {
                console.log(demandecongeUpdated)
                Swal.fire({
                  title: 'Success!',
                  text: 'Your operation was successful!',
                  icon: 'success',
                  confirmButtonText: 'OK'
                });
                this.initList();
                this.closeModal();

              }

            )
          }
        });

      }


    )

  };



 


  // Search function
 search() {
  this.applyFilters();
}

// Apply filters and pagination
applyFilters() {
  let filtered = [...this.demandecongeList];
  
  if (this.searchTerm) {
    const term = this.searchTerm.toLowerCase();
    filtered = filtered.filter(item => 
      item.code?.toLowerCase().includes(term) ||
      item.libelleBeneficiaire?.toLowerCase().includes(term) ||
      item.libelleTypeConge?.toLowerCase().includes(term) ||
      item.libelleServicee?.toLowerCase().includes(term) ||
      item.libellePoste?.toLowerCase().includes(term) ||
      item.dateDebut?.toLowerCase().includes(term) ||
      item.dateFin?.toLowerCase().includes(term)
    );
  }

  this.collectionSize = filtered.length;
  const startIndex = (this.page - 1) * this.pageSize;
  this.filteredListDbeneficiaire = filtered.slice(startIndex, startIndex + this.pageSize);
}

// Page change handler
onPageChange(page: number) {
  this.page = page;
  this.applyFilters();
}

  // openModal(selectedModal: NgbModal |any, soldeCongePassed: SoldeConge |null) {
  //   this.soldeCongeParam = soldeCongePassed;
  //   this.modal.open(selectedModal, {
  //     centered: false,
  //     size: "xl",
  //     backdrop: "static",
  //   });

  // }

  // openModal(selectedModal: any, demandeConge: any) {
  //   // Vous ne devez pas ajouter ": any" après le nom du paramètre
  //   this.soldeCongeParam = this.getSoldeCongeForDemande(demandeConge);
  //   this.modal.open(selectedModal, {
  //     centered: false,
  //     size: "xl",
  //     backdrop: "static",
  //   });
  // }

  openModal(selectedModal: any, demandeConge: any) {
    // Récupérer uniquement le solde spécifique pour cette demande
    this.soldeCongeParam = this.getSoldeCongeForDemande(demandeConge);
    
    // Important : créer une nouvelle liste contenant uniquement ce solde
    this.filteredListDSoldeConge = this.soldeCongeParam ? [this.soldeCongeParam] : [];
    
    this.modal.open(selectedModal, {
      centered: false,
      size: "xl",
      backdrop: "static",
    });
  }
  
  newSoldeConge(): SoldeConge {
    return new SoldeConge();
  }

  closeModal() {
    this.modal.dismissAll();
  }


  



delete(demandeconge: DemandeConge): void {
      Swal.fire({
        title: 'Êtes-vous sûr(e) de vouloir supprimer ?',
        text: "Cette action est irréversible.",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Oui, supprimer',
        cancelButtonText: 'Non, annuler',
      }).then((result) => {
        if (result.isConfirmed) {
          // Appel au service pour supprimer l'élément après confirmation
          this.demandeCongeService.deleteDemandeConge(demandeconge.uuid).subscribe(
            () => {
              Swal.fire(
                'Suppression !',
                'L\'élément a été supprimé avec succès.',
                'success'
              );
              // Rafraîchir la liste des éléments après la suppression
              this.initList();
            },
            (error) => {
              Swal.fire(
                'Erreur',
                'Une erreur est survenue lors de la suppression.',
                'error'
              );
            }
          );
        } else if (result.isDismissed) {
          Swal.fire(
            'Annulé',
            'Votre fichier est en sécurité :)',
            'info'
          );
        }
      });
  
  
  
  
  }
  

}


