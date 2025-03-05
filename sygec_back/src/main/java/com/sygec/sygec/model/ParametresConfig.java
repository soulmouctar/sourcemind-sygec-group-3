package com.sygec.sygec.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "parametres_config")
public class ParametresConfig extends AbstractEntity {

    @Column(name = "actif")
    private Boolean actif;

    @Column(name = "duree_min_conge")
    private Integer dureeMinConge;

    @Column(name = "duree_max_conge")
    private Integer dureeMaxConge;

    @Column(name = "delai_min_demande")
    private Integer delaiMinDemande;

    @Column(name = "solde_min_requis")
    private Integer soldeMinRequis;

    // Constructeur par défaut
    public ParametresConfig() {
        super();
        this.setUuid(UUID.randomUUID().toString());
        this.setSupprimer(false); // Initialiser explicitement le champ 'supprimer'
        this.actif = true;
    }

    // Getters et setters
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