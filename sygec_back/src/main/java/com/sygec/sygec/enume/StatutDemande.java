package com.sygec.sygec.enume;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum StatutDemande {
   VALIDER,ANNULER,REJETTER,EN_ATTENTE;
	  @JsonCreator
	    public static StatutDemande fromString(String value) {
	        if (value == null || value.trim().isEmpty()) {
	            return EN_ATTENTE; // valeur par défaut
	        }
	        try {
	            return StatutDemande.valueOf(value.toUpperCase());
	        } catch (IllegalArgumentException e) {
	            return EN_ATTENTE; // ou throw new IllegalArgumentException
	        }
	    }
}
