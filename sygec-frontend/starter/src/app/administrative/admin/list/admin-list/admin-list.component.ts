import { Component, OnInit } from '@angular/core';
import { Admin } from '../../../../models/admin';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AdminService } from '../../../../services/admin.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-admin-list',
  templateUrl: './admin-list.component.html',
  styleUrls: ['./admin-list.component.scss']
})
export class adminListComponent implements OnInit {

  adminParam: Admin | null = new Admin;

  adminList: Admin[] = [];

  filteredListDAdmin: Admin[] = []; // For search results
     // Pagination parameters
     page = 1;
     pageSize = 10;
     collectionSize = 0;
     
     // Search parameter
     searchTerm: string = '';

  constructor(
      private modal : NgbModal,
      private adminService : AdminService
     
    ){

  }
  
  ngOnInit(): void {
    this.initList();
  }

  initList(){
    this.adminService.getAllAdmin().subscribe(
      (list: Admin[])=>{
        console.log(list)
        this.adminList=list;
        this.filteredListDAdmin = list;
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
  let filtered = [...this.adminList];
  
  if (this.searchTerm) {
    const term = this.searchTerm.toLowerCase();
    filtered = filtered.filter(item => 
      item.email?.toLowerCase().includes(term)
    );
  }

  this.collectionSize = filtered.length;
  const startIndex = (this.page - 1) * this.pageSize;
  this.filteredListDAdmin = filtered.slice(startIndex, startIndex + this.pageSize);
}

// Page change handler
onPageChange(page: number) {
  this.page = page;
  this.applyFilters();
}

  openModal(selectedModal: NgbModal |any, AdminPassed: Admin |null) {
    this.adminParam = AdminPassed;
    this.modal.open(selectedModal, {
      centered: false,
      size: "xl",
      backdrop: "static",
    });

  }

  closeModal() {
    this.modal.dismissAll();
  }

  copyPassword(password: string) {
    navigator.clipboard.writeText(password);
    Swal.fire(
                    'Mot de passe copié',
                    'Le mot de passe a été copié dans le presse-papier.',
                    'success'
                  );
  }


  



//   delete(admin: Admin): void {
//     Swal.fire({
//       title: 'Êtes-vous sûr(e) de vouloir supprimer ?',
//       text: "Cette action est irréversible.",
//       icon: 'warning',
//       showCancelButton: true,
//       confirmButtonText: 'Oui, supprimer',
//       cancelButtonText: 'Non, annuler',
//     }).then((result) => {
//       if (result.isConfirmed) {
//         // Appel au service pour supprimer l'élément après confirmation
//         this.adminService.deleteAdmin(admin.uuid).subscribe(
//           () => {
//             Swal.fire(
//               'Suppression !',
//               'L\'élément a été supprimé avec succès.',
//               'success'
//             );
//             // Rafraîchir la liste des éléments après la suppression
//             this.initList();
//           },
//           (error) => {
//             Swal.fire(
//               'Erreur',
//               'Une erreur est survenue lors de la suppression.',
//               'error'
//             );
//           }
//         );
        
//       } else if (result.isDismissed) {
//         Swal.fire(
//           'Annulé',
//           'Votre fichier est en sécurité :)',
//           'info'
//         );
//       }
//     });




// }


}


