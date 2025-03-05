import { Component, OnInit } from '@angular/core';
import { Servicee } from '../../../../models/servicee';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ServiceeService } from '../../../../services/servicee.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-service-list',
  templateUrl: './service-list.component.html',
  styleUrls: ['./service-list.component.css']
})
export class ServiceListComponent implements OnInit {

  serviceParam: Servicee | null = new Servicee;

  serviceList: Servicee[] = [];
  

  filteredListDService: Servicee[] = []; // For search results
     // Pagination parameters
     page = 1;
     pageSize = 10;
     collectionSize = 0;
     
     // Search parameter
     searchTerm: string = '';

  constructor(
      private modal : NgbModal,
      private serviceService : ServiceeService
     
    ){

  }
  
  ngOnInit(): void {
    this.initList();
  }

  initList(){
    this.serviceService.getAllServicee().subscribe(
      (list: Servicee[])=>{
        console.log(list)
        this.serviceList=list;
        this.filteredListDService = list;
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
  let filtered = [...this.serviceList];
  
  if (this.searchTerm) {
    const term = this.searchTerm.toLowerCase();
    filtered = filtered.filter(item => 
      item.libelle?.toLowerCase().includes(term) ||
      item.description?.toLowerCase().includes(term)
 
    );
  }

  this.collectionSize = filtered.length;
  const startIndex = (this.page - 1) * this.pageSize;
  this.filteredListDService = filtered.slice(startIndex, startIndex + this.pageSize);
}

// Page change handler
onPageChange(page: number) {
  this.page = page;
  this.applyFilters();
}

  openModal(selectedModal: NgbModal |any, servicePassed: Servicee |null) {
    this.serviceParam = servicePassed;
    this.modal.open(selectedModal, {
      centered: false,
      size: "xl",
      backdrop: "static",
    });

  }

  closeModal() {
    this.modal.dismissAll();
  }


  



delete(service: Servicee): void {
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
          this.serviceService.deleteServicee(service.uuid).subscribe(
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

