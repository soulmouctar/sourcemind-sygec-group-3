package com.sygec.sygec.model;

import javax.persistence.Entity;

@Entity
public class TypeConge extends AbstractEntity{
	
	private String libelle;
	
	private boolean deductible;
	
	private String description;

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public boolean getDeductible() {
		return deductible;
	}

	public void setDeductible(boolean deductible) {
		this.deductible = deductible;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	

}
