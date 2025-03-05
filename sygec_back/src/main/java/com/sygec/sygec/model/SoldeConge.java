package com.sygec.sygec.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class SoldeConge extends AbstractEntity{
	
	private float soldeAnnuel;
	
	private float soldeRestandt;
	
	private int annee;
	
	 @OneToOne
	 @JoinColumn(name = "beneficiaire_uuid")
	 private Beneficiaire beneficiaire;

	public float getSoldeAnnuel() {
		return soldeAnnuel;
	}

	public void setSoldeAnnuel(float soldeAnnuel) {
		this.soldeAnnuel = soldeAnnuel;
	}

	public float getSoldeRestandt() {
		return soldeRestandt;
	}

	public void setSoldeRestandt(float soldeRestandt) {
		this.soldeRestandt = soldeRestandt;
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public Beneficiaire getBeneficiaire() {
		return beneficiaire;
	}

	public void setBeneficiaire(Beneficiaire beneficiaire) {
		this.beneficiaire = beneficiaire;
	}
    
    

}
