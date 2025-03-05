package com.sygec.sygec.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;

import com.sygec.sygec.dto.AdminDto;
import com.sygec.sygec.enume.EnumRole;







@Entity
public class Admin extends AbstractEntity{

		@Column(unique = true, nullable = false)
	    @Email(message = "Email should be valid")
	    private String Email; 
	
		private boolean online;
	    @Column(name = "HASHED_PASSWORD", nullable = true)
	    private String MotDePass;
	        
	    @ElementCollection
	    @CollectionTable(name = "ADMIN_ROLES", joinColumns = @JoinColumn(name = "USER_ID"))
	    @Enumerated(EnumType.STRING)
	    @Column(name = "ROLE")
	    private List<EnumRole> roles = new ArrayList<EnumRole>();
	  
	    boolean nonExpired = true;

	    boolean nonLocked = true;

	    boolean Enabled = false;
	    
	    boolean isFirstConnection = false;
	    
	    @OneToOne
	    Beneficiaire beneficiaire;
	    
	    public String getEmail() {
			return Email;
		}

		public String getMotDePass() {
			return MotDePass;
		}

	    public boolean isNonExpired() {
	        return nonExpired;
	    }

	   

		public boolean isNonLocked() {
	        return nonLocked;
	    }

	    public boolean isEnabled() {
	        return Enabled;
	    }

		public void setEmail(String email) {
			Email = email;
		}

		public void setMotDePass(String motDePass) {
			MotDePass = motDePass;
		}

	   

		public List<EnumRole> getRoles() {
			return roles;
		}

		public void setRoles(List<EnumRole> roles) {
			this.roles = roles;
		}

		public boolean isOnline() {
			return online;
		}

		public void setOnline(boolean online) {
			this.online = online;
		}

		public void setNonExpired(boolean nonExpired) {
	        this.nonExpired = nonExpired;
	    }

	    public void setNonLocked(boolean nonLocked) {
	        this.nonLocked = nonLocked;
	    }

	    public void setEnabled(boolean enabled) {
	        Enabled = enabled;
	    }

		public Beneficiaire getBeneficiaire() {
			return beneficiaire;
		}

		public void setBeneficiaire(Beneficiaire beneficiaire) {
			this.beneficiaire = beneficiaire;
		}

		public boolean isFirstConnection() {
			return isFirstConnection;
		}

		public void setFirstConnection(boolean isFirstConnection) {
			this.isFirstConnection = isFirstConnection;
		}
		
		
	    
	
	

	

	
	
	

}
