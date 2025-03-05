import { Component, OnInit } from '@angular/core';
import { DemandeConge } from '../../../../models/demande-conge';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DemandeCongeService } from '../../../../services/demande-conge.service';
import Swal from 'sweetalert2';
import { StatutDemande } from '../../../../models/statut-demande';
@Component({
  selector: 'app-demande-list',
  templateUrl: './demande-list.component.html',
  styleUrls: ['./demande-list.component.css']
})
export class DemandeListComponent implements OnInit {

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

