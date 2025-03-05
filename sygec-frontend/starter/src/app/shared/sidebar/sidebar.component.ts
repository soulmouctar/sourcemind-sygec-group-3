import { Component, AfterViewInit, OnInit, ViewChild, ElementRef } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ROUTES } from './menu-items';
import { RouteInfo } from './sidebar.metadata';
import { Router, ActivatedRoute } from '@angular/router';
import { Beneficiaire } from '../../models/beneficiaire';
import { FormGroup, FormBuilder } from '@angular/forms';
import { AuthenticationService } from '../../services/authentication.service';
import { TokenStorageService } from '../../../_services/token-storage.service';
import { BeneficiaireService } from '../../services/beneficiaire.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html'
})
export class SidebarComponent implements OnInit {
  showMenu = '';
  showSubMenu = '';
  public sidebarnavItems: RouteInfo[] = []; // Typage correct avec RouteInfo[]

  beneficiaire: Beneficiaire = new Beneficiaire(); // Simplifié
  beneficiaireList: Beneficiaire[] = [];
  beneficiaireForm: FormGroup;

  @ViewChild('sidebar') sidebar!: ElementRef;

  constructor(
    private modalService: NgbModal,
    private route: ActivatedRoute,
    private authentocationService: AuthenticationService,
    private tokenStorageService: TokenStorageService,
    private beneficiaireService: BeneficiaireService,
    private formBuilder: FormBuilder,
    private router: Router
  ) {
    // Initialiser le formulaire dans le constructeur
    this.beneficiaireForm = this.formBuilder.group({
      beneficiaire_uuid: ['']
    });
  }

  ngOnInit(): void {
    this.sidebarnavItems = ROUTES.filter(item => item);
    this.loadBeneficiaireData();
  }

  loadBeneficiaireData() {
    const beneficiaireUuid = this.beneficiaireForm.get('beneficiaire_uuid')?.value;
    if (!beneficiaireUuid) {
      return;
    }

    this.beneficiaireService.getOneBeneficiaire(beneficiaireUuid).subscribe({
      next: (response: any) => {
        if (response) {
          this.beneficiaire = {
            ...this.beneficiaire,
            email: response.uuid,
            libelleBeneficiaire: response.email
          };
        }
      },
      error: (error) => {
        console.error('Erreur lors du chargement du bénéficiaire:', error);
      }
    });
  }

  logout() {
    this.tokenStorageService.signOut();
    window.location.reload();
  }

  toggleSidebar() {
    this.sidebar?.nativeElement.classList.toggle('show');
  }

  addExpandClass(element: string) {
    this.showMenu = this.showMenu === element ? '0' : element;
  }

  addActiveClass(element: string) {
    this.showSubMenu = this.showSubMenu === element ? '0' : element;
  }
}