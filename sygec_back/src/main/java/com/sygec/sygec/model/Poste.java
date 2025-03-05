package com.sygec.sygec.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Poste extends AbstractEntity{
	
	private String libelle;
	
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "servicee_uuid")
	private Servicee servicee;

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Servicee getServicee() {
		return servicee;
	}

	public void setServicee(Servicee servicee) {
		this.servicee = servicee;
	}
	
	
	
	

}
