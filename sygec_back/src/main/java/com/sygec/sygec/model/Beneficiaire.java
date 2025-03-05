package com.sygec.sygec.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Beneficiaire  extends AbstractEntity{ 
	
	
	@Column(nullable = false)
	private String Nom;
	
	@Column(nullable = false)
	private String Prenom;
	
	@Column(nullable = false)
	private String Grade;
	
	@Column(nullable = false)
	private String Photo;
	
	@Column(nullable = false)
	private String Email;
	@Column(nullable = false)
	private String phone;
	
	@Column(nullable = false)
	private String matricule;
	
	
	@OneToOne(mappedBy = "beneficiaire")
    Admin user;
	
	@OneToOne(cascade = CascadeType.ALL)
	private StoredFile imgArcticle;
	
	private String imageUrl;
	

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

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	
	
	public String getPhoto() {
		return Photo;
	}

	public void setPhoto(String photo) {
		Photo = photo;
	}



	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "servicee_uuid")
	private Servicee servicee;


	
	public Servicee getServicee() {
		return servicee;
	}

	public void setServicee(Servicee servicee) {
		this.servicee = servicee;
	}

	public Admin getUser() {
		return user;
	}

	public void setUser(Admin user) {
		this.user = user;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public StoredFile getImgArcticle() {
		return imgArcticle;
	}

	public void setImgArcticle(StoredFile imgArcticle) {
		this.imgArcticle = imgArcticle;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
	
	

	
	
	
	
	
	

}
 