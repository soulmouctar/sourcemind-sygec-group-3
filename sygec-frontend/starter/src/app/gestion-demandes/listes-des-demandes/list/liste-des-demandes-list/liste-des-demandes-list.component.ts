import { Component, OnInit } from '@angular/core';
import { DemandeConge } from '../../../../models/demande-conge';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DemandeCongeService } from '../../../../services/demande-conge.service';
import Swal from 'sweetalert2';
import { StatutDemande } from '../../../../models/statut-demande';

@Component({
  selector: 'app-liste-des-demandes-list',
  templateUrl: './liste-des-demandes-list.component.html',
  styleUrls: ['./liste-des-demandes-list.component.scss']
})
export class ListeDesDemandesListComponent implements OnInit {

  demandeParam: DemandeConge | null = new DemandeConge;
  StatutDemande = StatutDemande;
  demandecongeList: DemandeConge[] = [];
  

  filteredListDbeneficiaire: DemandeConge[] = []; // For search results
     // Pagination parameters
     page = 1;
     pageSize = 10;
     collectionSize = 0;
     
     // Search parameter
     searchTerm: string = '';

  constructor(
      private modal : NgbModal,
      private demandeCongeService : DemandeCongeService
     
    ){

  }
  
  ngOnInit(): void {
    this.initList();
  }

  initList(){
    this.demandeCongeService.getAllDemandeCongefindAllIsNotValider().subscribe(
      (list: DemandeConge[])=>{
        console.log(list)
        this.demandecongeList=list;
        this.filteredListDbeneficiaire = list;
        this.collectionSize = list.length;
        this.applyFilters();
      }
    ); 
  }

  validerchef(demandeConge: DemandeConge) {
    // Mettre à jour le statut de la demande
    demandeConge.statut = StatutDemande.VALIDER;
    demandeConge.valider = true;
    demandeConge.rejetter = false;
    demandeConge.annuler = false;
  
    // Appeler le service pour mettre à jour la demande
    this.demandeCongeService.updateDemandeConge(demandeConge).subscribe(
      (demandecongeUpdated) => {
        console.log('Demande mise à jour', demandecongeUpdated);
        Swal.fire({
          title: 'Succès!',
          text: 'La demande a été validée avec succès.',
          icon: 'success',
          confirmButtonText: 'OK'
        });
        this.initList(); // Rafraîchir la liste
        this.closeModal(); // Fermer la modal (si applicable)
      },
      (error) => {
        console.error('Erreur lors de la mise à jour de la demande', error);
        Swal.fire({
          title: 'Erreur!',
          text: 'Une erreur est survenue lors de la mise à jour de la demande.',
          icon: 'error',
          confirmButtonText: 'OK'
        });
      }
    );
  }
  rejetterChef(demandeConge: DemandeConge) {
     // Mettre à jour le statut de la demande
     demandeConge.statut = StatutDemande.REJETTER;
     demandeConge.valider = false;
     demandeConge.rejetter = true;
     demandeConge.annuler = false;
   
     // Appeler le service pour mettre à jour la demande
     this.demandeCongeService.updateDemandeConge(demandeConge).subscribe(
       (demandecongeUpdated) => {
         console.log('Demande mise à jour', demandecongeUpdated);
         Swal.fire({
           title: 'Succès!',
           text: 'La demande a été rejettée avec succès.',
           icon: 'success',
           confirmButtonText: 'OK'
         });
         this.initList(); // Rafraîchir la liste
         this.closeModal(); // Fermer la modal (si applicable)
       },
       (error) => {
         console.error('Erreur lors de la mise à jour de la demande', error);
         Swal.fire({
           title: 'Erreur!',
           text: 'Une erreur est survenue lors de la mise à jour de la demande.',
           icon: 'error',
           confirmButtonText: 'OK'
         });
       }
     );

  };

  annulerchef(demandeConge: DemandeConge) {
    // Mettre à jour le statut de la demande
    demandeConge.statut = StatutDemande.ANNULER;
    demandeConge.valider = false;
    demandeConge.rejetter = false;
    demandeConge.annuler = true;
  
    // Appeler le service pour mettre à jour la demande
    this.demandeCongeService.updateDemandeConge(demandeConge).subscribe(
      (demandecongeUpdated) => {
        console.log('Demande mise à jour', demandecongeUpdated);
        Swal.fire({
          title: 'Succès!',
          text: 'La demande a été annulée avec succès.',
          icon: 'success',
          confirmButtonText: 'OK'
        });
        this.initList(); // Rafraîchir la liste
        this.closeModal(); // Fermer la modal (si applicable)
      },
      (error) => {
        console.error('Erreur lors de la mise à jour de la demande', error);
        Swal.fire({
          title: 'Erreur!',
          text: 'Une erreur est survenue lors de la mise à jour de la demande.',
          icon: 'error',
          confirmButtonText: 'OK'
        });
      }
    );

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

  openModal(selectedModal: NgbModal |any, demandecongePassed: DemandeConge |null) {
    this.demandeParam = demandecongePassed;
    this.modal.open(selectedModal, {
      centered: false,
      size: "xl",
      backdrop: "static",
    });

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

