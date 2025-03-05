import { Component, OnInit } from '@angular/core';
import { Beneficiaire } from '../../../../models/beneficiaire';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { BeneficiaireService } from '../../../../services/beneficiaire.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-beneficiaire-list',
  templateUrl: './beneficiaire-list.component.html',
  styleUrls: ['./beneficiaire-list.component.scss']
})
export class beneficiaireListComponent implements OnInit {

 beneficiaireParam: Beneficiaire | null = new Beneficiaire;

  beneficiaireList: Beneficiaire[] = [];
  

  filteredListDBeneficiaire: Beneficiaire[] = []; // For search results
     // Pagination parameters
     page = 1;
     pageSize = 10;
     collectionSize = 0;
     
     // Search parameter
     searchTerm: string = '';

  constructor(
      private modal : NgbModal,
      private beneficiaireService : BeneficiaireService
     
    ){

  }
  
  ngOnInit(): void {
    this.initList();
  }

  initList(){
    this.beneficiaireService.getAllBeneficiaire().subscribe(
      (list: Beneficiaire[])=>{
        console.log(list)
        this.beneficiaireList=list;
        this.filteredListDBeneficiaire = list;
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
  let filtered = [...this.beneficiaireList];
  
  if (this.searchTerm) {
    const term = this.searchTerm.toLowerCase();
    filtered = filtered.filter(item => 
      item.prenom?.toLowerCase().includes(term) ||
      item.nom?.toLowerCase().includes(term) ||
      item.email?.toLowerCase().includes(term) ||
      item.phone?.toLowerCase().includes(term) ||
      item.grade?.toLowerCase().includes(term) 
    );
  }

  this.collectionSize = filtered.length;
  const startIndex = (this.page - 1) * this.pageSize;
  this.filteredListDBeneficiaire = filtered.slice(startIndex, startIndex + this.pageSize);
}

// Page change handler
onPageChange(page: number) {
  this.page = page;
  this.applyFilters();
}

  openModal(selectedModal: NgbModal |any, BeneficiairePassed: Beneficiaire |null) {
    this.beneficiaireParam = BeneficiairePassed;
    this.modal.open(selectedModal, {
      centered: false,
      size: "xl",
      backdrop: "static",
    });

  }

  closeModal() {
    this.modal.dismissAll();
  }


  



delete(Beneficiaire: Beneficiaire): void {
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
          this.beneficiaireService.deleteBeneficiaire(Beneficiaire.uuid).subscribe(
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

