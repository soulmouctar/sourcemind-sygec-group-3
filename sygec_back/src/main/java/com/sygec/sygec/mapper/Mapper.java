package com.sygec.sygec.mapper;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.sygec.sygec.dto.AdminDto;
import com.sygec.sygec.dto.BeneficiaireDto;
import com.sygec.sygec.dto.DemandeCongeDto;
import com.sygec.sygec.dto.ParametresConfigDto;
import com.sygec.sygec.dto.PosteDto;
import com.sygec.sygec.dto.ServiceeDto;
import com.sygec.sygec.dto.SoldeCongeDto;
import com.sygec.sygec.dto.StoredFileDto;
import com.sygec.sygec.dto.StoredFileInfoDto;
import com.sygec.sygec.dto.TypeCongeDto;
import com.sygec.sygec.enume.EnumRole;
import com.sygec.sygec.model.Admin;
import com.sygec.sygec.model.Beneficiaire;
import com.sygec.sygec.model.DemandeConge;
import com.sygec.sygec.model.ParametresConfig;
import com.sygec.sygec.model.Poste;
import com.sygec.sygec.model.Servicee;
import com.sygec.sygec.model.SoldeConge;
import com.sygec.sygec.model.StoredFile;
import com.sygec.sygec.model.TypeConge;



public class Mapper {
	
	// Admin Table
	
			public static UserDetails toUserDetails(Admin user) {
				Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
				UserDetails userdetails = new User(user.getEmail(), user.getMotDePass(), authorities);
				return userdetails;
			}

			public static Admin toUser(AdminDto user) {
				Admin admin = new Admin();
				admin.setEmail(user.getEmail());
				admin.setRoles(user.getRoles());
				return admin;
			}
			
			public static AdminDto toAdminDto(Admin user) {
				AdminDto adminDto = new AdminDto();
				adminDto.setUuid(user.getUuid());
				adminDto.setEmail(user.getEmail());
				adminDto.setMotDePass(user.getMotDePass());
				adminDto.setOnline(user.isOnline());
				adminDto.setEnabled(user.isEnabled());
				adminDto.setNonExpired(user.isNonExpired());
				adminDto.setNonLocked(user.isNonLocked());
				adminDto.setRoles(user.getRoles());
				adminDto.setFirstConnection(user.isFirstConnection());
				
				return adminDto;
			}
			
			public static AdminDto toUser(BeneficiaireDto beneficiaireDto) {
				Admin admin = new Admin();
				admin.setEmail(beneficiaireDto.getEmail());
				List<EnumRole> enumRoles = new ArrayList<EnumRole>();
				enumRoles.add(beneficiaireDto.getRole());
				beneficiaireDto.setRoles(enumRoles);
				admin.setRoles(beneficiaireDto.getRoles());
				return toAdminDto(admin);
			}
			
			//Storefile
			 public static StoredFileDto toStoreFileDto(StoredFile fileDB) {
			        StoredFileDto result = new StoredFileDto();
			       // result.setData(fileDB.getData());
			        result.setBytes(fileDB.getBytes());
			        result.setName(fileDB.getName());
			        result.setUuid(fileDB.getUuid());
			        result.setType(fileDB.getType());
			        return result;
			    }

			    public static StoredFileInfoDto toStoredFileInfoDto(StoredFile fileDB) {
			        StoredFileInfoDto result = new StoredFileInfoDto();
			        result.setName(fileDB.getName());
			        result.setUuid(fileDB.getUuid());
			        return result;
			    }
		
			    
			    public static BeneficiaireDto toClasseDto(Beneficiaire beneficiaire) {
			        BeneficiaireDto dto = new BeneficiaireDto();
			        
			        // Gérer l'image si elle existe
			        if (beneficiaire.getImgArcticle() != null) {
			            try {
			                byte[] imageBytes = beneficiaire.getImgArcticle().getBytes();
			                byte[] encodeBase64 = Base64.getEncoder().encode(imageBytes);
			                String base64Encoded = new String(encodeBase64, "UTF-8");
			                String img = "data:" + beneficiaire.getImgArcticle().getType() + ";base64," + base64Encoded;
			                dto.setImageUrl(img);
			                dto.setUuidimgArcticle(beneficiaire.getImgArcticle().getUuid());
			            } catch (Exception e) {
			                // Log l'erreur ou gérer l'exception de manière appropriée
			                e.printStackTrace();
			            }
			        }

			        // Remplir les autres champs du DTO
			        dto.setUuid(beneficiaire.getUuid());
			        dto.setLibelleBeneficiaire(beneficiaire.getEmail());
			        dto.setNom(beneficiaire.getNom());
			        dto.setPrenom(beneficiaire.getPrenom());
			        dto.setGrade(beneficiaire.getGrade());
			        dto.setPhoto(beneficiaire.getPhoto());
			        dto.setEmail(beneficiaire.getEmail());
			        dto.setPhone(beneficiaire.getPhone());
			        dto.setMatricule(beneficiaire.getMatricule());
			        
			        return dto;
			    }
			
			// DeamndeConge Table
			public static DemandeCongeDto toClasseDto(DemandeConge demandeConge) {
				DemandeCongeDto dto = new DemandeCongeDto();
				dto.setUuid(demandeConge.getUuid());
				dto.setCode(demandeConge.getCode());
				dto.setDateDebut(demandeConge.getDateDebut());
				dto.setDateFin(demandeConge.getDateFin());
				dto.setMotif(demandeConge.getMotif());
				dto.setStatut(demandeConge.getStatut());
				dto.setBeneficiaire_uuid(demandeConge.getBeneficiaire().getUuid());
				dto.setLibelleBeneficiaire(demandeConge.getBeneficiaire().getEmail()+" "+ demandeConge.getBeneficiaire().getNom()+" "+ demandeConge.getBeneficiaire().getPrenom());
				dto.setPoste_uuid(demandeConge.getPoste().getUuid());
				dto.setLibellePoste(demandeConge.getPoste().getLibelle());
				dto.setServicee_uuid(demandeConge.getServicee().getUuid());
				dto.setLibelleServicee(demandeConge.getServicee().getLibelle());
				dto.setTypeConge_uuid(demandeConge.getTypeConge().getUuid());
				dto.setLibelleTypeConge(demandeConge.getTypeConge().getLibelle());
				dto.setValider(demandeConge.isValider());
				dto.setRejetter(demandeConge.isRejetter());
				dto.setAnnuler(demandeConge.isAnnuler());
				dto.setNombreJours(demandeConge.getNombreJours());
				return dto;
			}
			
			// Poste Table
			public static PosteDto toClasseDto(Poste poste) {
				PosteDto dto = new PosteDto();
				dto.setUuid(poste.getUuid());
				dto.setLibelle(poste.getLibelle());
				dto.setDescription(poste.getDescription());
				dto.setServicee_uuid(poste.getServicee().getUuid());
				dto.setLibelleService(poste.getServicee().getLibelle());
				return dto;
			}
			
			// Service Table
			public static ServiceeDto toClasseDto(Servicee servicee) {
				ServiceeDto dto = new ServiceeDto();
				dto.setUuid(servicee.getUuid());
				dto.setLibelle(servicee.getLibelle());
				dto.setDescription(servicee.getDescription());
				return dto;
			}
			
			// SoldeConge Table
			public static SoldeCongeDto toClasseDto(SoldeConge soldeConge) {
				SoldeCongeDto dto = new SoldeCongeDto();
				dto.setUuid(soldeConge.getUuid());
				dto.setSoldeAnnuel(soldeConge.getSoldeAnnuel());
				dto.setSoldeRestandt(soldeConge.getSoldeRestandt());
				dto.setAnnee(soldeConge.getAnnee());
				dto.setBeneficiaire_uuid(soldeConge.getBeneficiaire().getUuid());
				dto.setLibelleBeneficiaire(soldeConge.getBeneficiaire().getEmail()+" "+soldeConge.getBeneficiaire().getNom()+" "+soldeConge.getBeneficiaire().getPrenom());
				return dto;
			}
			
			// TypeConge Table
			public static TypeCongeDto toClasseDto(TypeConge typeConge) {
				TypeCongeDto dto = new TypeCongeDto();
				dto.setUuid(typeConge.getUuid());
				dto.setLibelle(typeConge.getLibelle());
				dto.setDeductible(typeConge.getDeductible());
				dto.setDescription(typeConge.getDescription());
				return dto;
			}
			
			// Parametre Config Table
						public static ParametresConfigDto toClasseDto(ParametresConfig parametresConfig) {
							ParametresConfigDto dto = new ParametresConfigDto();
							dto.setUuid(parametresConfig.getUuid());
							dto.setDureeMinConge(parametresConfig.getDureeMinConge());
							dto.setDureeMaxConge(parametresConfig.getDureeMaxConge());
							dto.setDelaiMinDemande(parametresConfig.getDelaiMinDemande());
							dto.setSoldeMinRequis(parametresConfig.getSoldeMinRequis());
							return dto;
						}
			

}
