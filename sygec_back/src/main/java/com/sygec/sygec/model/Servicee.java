package com.sygec.sygec.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Servicee extends AbstractEntity{
	
	private String libelle;
	
	private String description;
	
	@OneToMany (mappedBy = "servicee",  fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Poste> poste;
	

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

	public List<Poste> getPoste() {
		return poste;
	}

	public void setPoste(List<Poste> poste) {
		this.poste = poste;
	}

	
	
	
	
	

}
