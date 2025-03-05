import { Component, OnInit } from '@angular/core';
import { Poste } from '../../../../models/poste';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PosteService } from '../../../../services/poste.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-poste-list',
  templateUrl: './poste-list.component.html',
  styleUrls: ['./poste-list.component.css']
})
export class PosteListComponent implements OnInit {

  posteParam: Poste | null = new Poste;

  posteList: Poste[] = [];
  

  filteredListDPoste: Poste[] = []; // For search results
     // Pagination parameters
     page = 1;
     pageSize = 10;
     collectionSize = 0;
     
     // Search parameter
     searchTerm: string = '';

  constructor(
      private modal : NgbModal,
      private posteService : PosteService
     
    ){

  }
  
  ngOnInit(): void {
    this.initList();
  }

  initList(){
    this.posteService.getAllPoste().subscribe(
      (list: Poste[])=>{
        console.log(list)
        this.posteList=list;
        this.filteredListDPoste = list;
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
  let filtered = [...this.posteList];
  
  if (this.searchTerm) {
    const term = this.searchTerm.toLowerCase();
    filtered = filtered.filter(item => 
      item.libelle?.toLowerCase().includes(term) ||
      item.description?.toLowerCase().includes(term)
    );
  }

  this.collectionSize = filtered.length;
  const startIndex = (this.page - 1) * this.pageSize;
  this.filteredListDPoste = filtered.slice(startIndex, startIndex + this.pageSize);
}

// Page change handler
onPageChange(page: number) {
  this.page = page;
  this.applyFilters();
}

  openModal(selectedModal: NgbModal |any, postePassed: Poste |null) {
    this.posteParam = postePassed;
    this.modal.open(selectedModal, {
      centered: false,
      size: "xl",
      backdrop: "static",
    });

  }

  closeModal() {
    this.modal.dismissAll();
  }


  



delete(poste: Poste): void {
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
          this.posteService.deletePoste(poste.uuid).subscribe(
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


