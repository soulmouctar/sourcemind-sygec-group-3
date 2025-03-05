import { StatutDemande } from "./statut-demande";

export class DemandeConge {
     uuid :  string = "";
     code :  string = "";
     dateDebut : string = "";
     dateFin :  string = "";
     statut :  StatutDemande;
     motif :  string = "";
     beneficiaire_uuid :  string = "";
     libelleBeneficiaire: string = "";
     poste_uuid :  string = "";
     libellePoste: string = "";
     servicee_uuid : string = "";
     libelleServicee: string = "";
     typeConge_uuid :  string = "";
     libelleTypeConge: string = "";
     valider : boolean = false;
     rejetter : boolean = false;
     annuler : boolean = false;
     nombreJours : number ;
}
