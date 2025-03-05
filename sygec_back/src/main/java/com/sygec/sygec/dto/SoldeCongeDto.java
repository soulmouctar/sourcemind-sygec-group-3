package com.sygec.sygec.dto;

public class SoldeCongeDto {
	
	private String uuid;
	
	private float soldeAnnuel;
	
	private float soldeRestandt;
	
	private int annee;
	
    private String beneficiaire_uuid;
    
    private String libelleBeneficiaire;
    
    

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

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

	public String getBeneficiaire_uuid() {
		return beneficiaire_uuid;
	}

	public void setBeneficiaire_uuid(String beneficiaire_uuid) {
		this.beneficiaire_uuid = beneficiaire_uuid;
	}

	public String getLibelleBeneficiaire() {
		return libelleBeneficiaire;
	}

	public void setLibelleBeneficiaire(String libelleBeneficiaire) {
		this.libelleBeneficiaire = libelleBeneficiaire;
	}
	
	
	
	

    

}
