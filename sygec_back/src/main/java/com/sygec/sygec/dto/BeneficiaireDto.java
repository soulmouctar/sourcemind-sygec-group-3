package com.sygec.sygec.dto;

import java.util.ArrayList;
import java.util.List;

import com.sygec.sygec.enume.EnumRole;
import com.sygec.sygec.model.SoldeConge;



public class BeneficiaireDto { 
	
	private String Uuid;
	

	private String Nom;
	

	private String Prenom;
	

	private String Grade;
	
	private String Photo;
	

	private String Email;
	private String phone;
	
	private String uuidimgArcticle;
	
	private String imgArcticle;
	
	private String imageUrl;
	
	private String matricule;
	
	private String libelleBeneficiaire;
	
	private EnumRole role;
	

//	private String MotDePass;
	
    private List<EnumRole> roles = new ArrayList<EnumRole>();


	public String getNom() {
		return Nom;
	}

	public void setNom(String nom) {
		Nom = nom;
	}

	public String getPrenom() {
		return Prenom;
	}

	public void setPrenom(String prenom) {
		Prenom = prenom;
	}

	public String getGrade() {
		return Grade;
	}

	public void setGrade(String grade) {
		Grade = grade;
	}

	public String getPhoto() {
		return Photo;
	}

	public void setPhoto(String photo) {
		Photo = photo;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}
	

	private String service_uuid;


	public String getService_uuid() {
		return service_uuid;
	}

	public void setService_uuid(String service_uuid) {
		this.service_uuid = service_uuid;
	}

	public String getUuid() {
		return Uuid;
	}

	public void setUuid(String uuid) {
		Uuid = uuid;
	}

	public List<EnumRole> getRoles() {
		return roles;
	}

	public void setRoles(List<EnumRole> roles) {
		this.roles = roles;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public EnumRole getRole() {
		return role;
	}

	public void setRole(EnumRole role) {
		this.role = role;
	}

	public String getLibelleBeneficiaire() {
		return libelleBeneficiaire;
	}

	public void setLibelleBeneficiaire(String libelleBeneficiaire) {
		this.libelleBeneficiaire = libelleBeneficiaire;
	}

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public String getUuidimgArcticle() {
		return uuidimgArcticle;
	}

	public void setUuidimgArcticle(String uuidimgArcticle) {
		this.uuidimgArcticle = uuidimgArcticle;
	}

	public String getImgArcticle() {
		return imgArcticle;
	}

	public void setImgArcticle(String imgArcticle) {
		this.imgArcticle = imgArcticle;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	

	
	

	


	

	
	
	
	
	
	
	

}
 