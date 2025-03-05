package com.sygec.sygec.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.sygec.sygec.enume.StatutDemande;


@Entity
public class DemandeConge extends AbstractEntity{
	
	@Column(nullable = false)
	private String code;
	
	 @Column(nullable = false)
	private String dateDebut;
	
	 @Column(nullable = false)
	private String dateFin;
	
	 @Enumerated(EnumType.STRING) 
	private StatutDemande statut;
	
	private String motif;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "beneficiaire_uuid")
	private Beneficiaire beneficiaire;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "poste_uuid")
	private Poste poste;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "servicee_uuid")
	private Servicee servicee;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "typeConge_uuid")
	private TypeConge typeConge;
	
	private boolean valider;
	
	private boolean rejetter;
	
	private boolean annuler;
	
	 @Column(name = "nombre_jours")
	    private Integer nombreJours;

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

	public Beneficiaire getBeneficiaire() {
		return beneficiaire;
	}

	public void setBeneficiaire(Beneficiaire beneficiaire) {
		this.beneficiaire = beneficiaire;
	}

	public Poste getPoste() {
		return poste;
	}

	public void setPoste(Poste poste) {
		this.poste = poste;
	}

	public Servicee getServicee() {
		return servicee;
	}

	public void setServicee(Servicee servicee) {
		this.servicee = servicee;
	}

	public TypeConge getTypeConge() {
		return typeConge;
	}

	public void setTypeConge(TypeConge typeConge) {
		this.typeConge = typeConge;
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

	public Integer getNombreJours() {
		return nombreJours;
	}

	public void setNombreJours(Integer nombreJours) {
		this.nombreJours = nombreJours;
	}
	
	
	
	
	

}
