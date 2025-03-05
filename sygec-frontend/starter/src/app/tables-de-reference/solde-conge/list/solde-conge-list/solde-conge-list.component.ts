import { Component, OnInit } from '@angular/core';
import { SoldeConge } from '../../../../models/solde-conge';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SoldeCongeService } from '../../../../services/solde-conge.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-solde-conge-list',
  templateUrl: './solde-conge-list.component.html',
  styleUrls: ['./solde-conge-list.component.css']
})
export class SoldeCongeListComponent implements OnInit {

  soldeCongeParam: SoldeConge | null = new SoldeConge;

  soldeCongeList: SoldeConge[] = [];
  

  filteredListDSoldeConge: SoldeConge[] = []; // For search results
     // Pagination parameters
     page = 1;
     pageSize = 10;
     collectionSize = 0;
     
     // Search parameter
     searchTerm: string = '';

  constructor(
      private modal : NgbModal,
      private soldeCongeService : SoldeCongeService
     
    ){

  }
  
  ngOnInit(): void {
    this.initList();
  }

  initList(){
    this.soldeCongeService.getAllSoldeConge().subscribe(
      (list: SoldeConge[])=>{
        console.log(list)
        this.soldeCongeList=list;
        this.filteredListDSoldeConge = list;
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
  let filtered = [...this.soldeCongeList];
  
  if (this.searchTerm) {
    const term = this.searchTerm.toLowerCase();
    filtered = filtered.filter(item => 
      item.libelleBeneficiaire?.toLowerCase().includes(term)
    );
  }

  this.collectionSize = filtered.length;
  const startIndex = (this.page - 1) * this.pageSize;
  this.filteredListDSoldeConge = filtered.slice(startIndex, startIndex + this.pageSize);
}

// Page change handler
onPageChange(page: number) {
  this.page = page;
  this.applyFilters();
}

  openModal(selectedModal: NgbModal |any, soldeCongePassed: SoldeConge |null) {
    this.soldeCongeParam = soldeCongePassed;
    this.modal.open(selectedModal, {
      centered: false,
      size: "xl",
      backdrop: "static",
    });

  }

  closeModal() {
    this.modal.dismissAll();
  }


  



delete(soldeConge: SoldeConge): void {
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
          this.soldeCongeService.deleteSoldeConge(soldeConge.uuid).subscribe(
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


