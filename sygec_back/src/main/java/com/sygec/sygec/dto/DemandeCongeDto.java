package com.sygec.sygec.dto;


import com.sygec.sygec.enume.StatutDemande;

public class DemandeCongeDto {
	
	private String uuid;
	
	private String code;
	
	private String dateDebut;
	
	private String dateFin;
	
	private StatutDemande statut;
	
	private String motif;
	

	private String beneficiaire_uuid;
	
	private String libelleBeneficiaire;
	

	private String poste_uuid;
	
	private String libellePoste;
	

	private String servicee_uuid;
	
	private String libelleServicee;
	

	private String typeConge_uuid;
	
	private String libelleTypeConge;
	
private boolean valider;
	
	private boolean rejetter;
	
	private boolean annuler;
	
	 private Integer nombreJours;

	

	public String getUuid() {
		return uuid;
	}


	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getDateDebut() {
		return dateDebut;
	}


	public void setDateDebut(String dateDebut) {
		this.dateDebut = dateDebut;
	}


	public String getDateFin() {
		return dateFin;
	}


	public void setDateFin(String dateFin) {
		this.dateFin = dateFin;
	}


	public StatutDemande getStatut() {
		return statut;
	}


	public void setStatut(StatutDemande statut) {
		this.statut = statut;
	}


	public String getMotif() {
		return motif;
	}


	public void setMotif(String motif) {
		this.motif = motif;
	}


	public String getBeneficiaire_uuid() {
		return beneficiaire_uuid;
	}


	public void setBeneficiaire_uuid(String beneficiaire_uuid) {
		this.beneficiaire_uuid = beneficiaire_uuid;
	}


	public String getPoste_uuid() {
		return poste_uuid;
	}


	public void setPoste_uuid(String poste_uuid) {
		this.poste_uuid = poste_uuid;
	}


	public String getServicee_uuid() {
		return servicee_uuid;
	}


	public void setServicee_uuid(String servicee_uuid) {
		this.servicee_uuid = servicee_uuid;
	}


	public String getTypeConge_uuid() {
		return typeConge_uuid;
	}


	public void setTypeConge_uuid(String typeConge_uuid) {
		this.typeConge_uuid = typeConge_uuid;
	}


	public boolean isValider() {
		return valider;
	}


	public void setValider(boolean valider) {
		this.valider = valider;
	}


	public boolean isRejetter() {
		return rejetter;
	}


	public void setRejetter(boolean rejetter) {
		this.rejetter = rejetter;
	}


	public boolean isAnnuler() {
		return annuler;
	}


	public void setAnnuler(boolean annuler) {
		this.annuler = annuler;
	}


	public String getLibelleBeneficiaire() {
		return libelleBeneficiaire;
	}


	public void setLibelleBeneficiaire(String libelleBeneficiaire) {
		this.libelleBeneficiaire = libelleBeneficiaire;
	}


	public String getLibelleServicee() {
		return libelleServicee;
	}


	public void setLibelleServicee(String libelleServicee) {
		this.libelleServicee = libelleServicee;
	}


	public String getLibelleTypeConge() {
		return libelleTypeConge;
	}


	public void setLibelleTypeConge(String libelleTypeConge) {
		this.libelleTypeConge = libelleTypeConge;
	}


	public String getLibellePoste() {
		return libellePoste;
	}


	public void setLibellePoste(String libellePoste) {
		this.libellePoste = libellePoste;
	}


	public Integer getNombreJours() {
		return nombreJours;
	}


	public void setNombreJours(Integer nombreJours) {
		this.nombreJours = nombreJours;
	}
	
	
	
	
	
	
	
	

	
	

}
