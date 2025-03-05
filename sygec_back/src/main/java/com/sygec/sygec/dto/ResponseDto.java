package com.sygec.sygec.dto;

public class ResponseDto {
	private int code;
	private String titre;
	private String description;
	private String statut;
	
	public ResponseDto() {
		
	}
	
	
	
	public ResponseDto(int code, String titre, String description, String statut) {
		super();
		this.code = code;
		this.titre = titre;
		this.description = description;
		this.statut = statut;
	}



	public String getStatut() {
		return statut;
	}


	public void setStatut(String statut) {
		this.statut = statut;
	}


	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	
	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	



}
