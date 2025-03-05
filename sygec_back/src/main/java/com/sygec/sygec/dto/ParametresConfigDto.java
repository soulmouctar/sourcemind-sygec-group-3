package com.sygec.sygec.dto;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;

public class ParametresConfigDto {
 
    private String uuid;

 
    private Boolean actif;


    private Integer dureeMinConge;

  
    private Integer dureeMaxConge;

    
    private Integer delaiMinDemande;

   
    private Integer soldeMinRequis;


	public String getUuid() {
		return uuid;
	}


	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	public Boolean getActif() {
		return actif;
	}


	public void setActif(Boolean actif) {
		this.actif = actif;
	}


	public Integer getDureeMinConge() {
		return dureeMinConge;
	}


	public void setDureeMinConge(Integer dureeMinConge) {
		this.dureeMinConge = dureeMinConge;
	}


	public Integer getDureeMaxConge() {
		return dureeMaxConge;
	}


	public void setDureeMaxConge(Integer dureeMaxConge) {
		this.dureeMaxConge = dureeMaxConge;
	}


	public Integer getDelaiMinDemande() {
		return delaiMinDemande;
	}


	public void setDelaiMinDemande(Integer delaiMinDemande) {
		this.delaiMinDemande = delaiMinDemande;
	}


	public Integer getSoldeMinRequis() {
		return soldeMinRequis;
	}


	public void setSoldeMinRequis(Integer soldeMinRequis) {
		this.soldeMinRequis = soldeMinRequis;
	}




    
    

}
