package com.sygec.sygec.dto;

import java.util.ArrayList;
import java.util.List;

import com.sygec.sygec.enume.EnumRole;



public class AdminDto { 
	
	private String Uuid;
	
	private String Email;
	
	private String MotDePass;
	
	private boolean online;
	
    private boolean nonExpired ;
    
    private boolean nonLocked;
    
    private boolean Enabled;
    
    private String uuidBeneficiaire;
    
    private List<EnumRole> roles = new ArrayList<EnumRole>();
    
    private boolean isFirstConnection = false;
	
	
	

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getMotDePass() {
		return MotDePass;
	}

	public void setMotDePass(String motDePass) {
		MotDePass = motDePass;
	}
	
	

	public String getUuid() {
		return Uuid;
	}

	public void setUuid(String uuid) {
		Uuid = uuid;
	}

	public String getUuidBeneficiaire() {
		return uuidBeneficiaire;
	}

	public void setUuidBeneficiaire(String uuidBeneficiaire) {
		this.uuidBeneficiaire = uuidBeneficiaire;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

    public List<EnumRole> getRoles() {
        return roles;
    }

    public void setRoles(List<EnumRole> roles) {
        this.roles = roles;
    }

	public boolean isNonExpired() {
		return nonExpired;
	}

	public void setNonExpired(boolean nonExpired) {
		this.nonExpired = nonExpired;
	}

	public boolean isNonLocked() {
		return nonLocked;
	}

	public void setNonLocked(boolean nonLocked) {
		this.nonLocked = nonLocked;
	}

	public boolean isEnabled() {
		return Enabled;
	}

	public void setEnabled(boolean enabled) {
		Enabled = enabled;
	}

	public boolean isFirstConnection() {
		return isFirstConnection;
	}

	public void setFirstConnection(boolean isFirstConnection) {
		this.isFirstConnection = isFirstConnection;
	}
	
	

	
	
	

}
 