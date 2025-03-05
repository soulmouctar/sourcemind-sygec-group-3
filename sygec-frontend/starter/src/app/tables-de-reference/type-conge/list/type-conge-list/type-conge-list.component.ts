import { Component, OnInit } from '@angular/core';
import { TypeConge } from '../../../../models/type-conge';
import { TypeCongeService } from '../../../../services/type-conge.service';
import Swal from 'sweetalert2';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';



@Component({
  selector: 'app-type-conge-list',
  templateUrl: './type-conge-list.component.html',
  styleUrls: ['./type-conge-list.component.css']
})
export class TypeCongeListComponent implements OnInit {

  typeCongeParam: TypeConge | null = new TypeConge;

  typeCongeList: TypeConge[] = [];
  

  filteredListDTypeConge: TypeConge[] = []; // For search results
     // Pagination parameters
     page = 1;
     pageSize = 10;
     collectionSize = 0;
     
     // Search parameter
     searchTerm: string = '';

  constructor(
      private modal : NgbModal,
      private typeCongeService : TypeCongeService
     
    ){

  }
  
  ngOnInit(): void {
    this.initList();
  }

  initList(){
    this.typeCongeService.getAllTypeConge().subscribe(
      (list: TypeConge[])=>{
        console.log(list)
        this.typeCongeList=list;
        this.filteredListDTypeConge = list;
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
  let filtered = [...this.typeCongeList];
  
  if (this.searchTerm) {
    const term = this.searchTerm.toLowerCase();
    filtered = filtered.filter(item => 
      item.libelle?.toLowerCase().includes(term) ||
      item.deductible?.toLowerCase().includes(term) ||
      item.description?.toLowerCase().includes(term)
    );
  }

  this.collectionSize = filtered.length;
  const startIndex = (this.page - 1) * this.pageSize;
  this.filteredListDTypeConge = filtered.slice(startIndex, startIndex + this.pageSize);
}

// Page change handler
onPageChange(page: number) {
  this.page = page;
  this.applyFilters();
}

  openModal(selectedModal: NgbModal |any, typeCongePassed: TypeConge |null) {
    this.typeCongeParam = typeCongePassed;
    this.modal.open(selectedModal, {
      centered: false,
      size: "xl",
      backdrop: "static",
    });

  }

  closeModal() {
    this.modal.dismissAll();
  }


  



delete(typeConge: TypeConge): void {
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
          this.typeCongeService.deleteTypeConge(typeConge.uuid).subscribe(
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

