package com.sygec.sygec.serviceImpl;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sygec.sygec.dto.AdminDto;
import com.sygec.sygec.mapper.Mapper;
import com.sygec.sygec.model.Admin;
import com.sygec.sygec.model.Beneficiaire;
import com.sygec.sygec.repository.AdminRepository;
import com.sygec.sygec.repository.BeneficiaireRepository;
import com.sygec.sygec.service.AdminService;

import ch.qos.logback.classic.Logger;

@Service
public class AdminServiceImpl implements AdminService {

	Logger logger = (Logger) LoggerFactory.getLogger(AdminServiceImpl.class);
	
    @Autowired
    AdminRepository adminRepository;
    
    @Autowired
    BeneficiaireRepository beneficiaireRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public AdminDto save(AdminDto userDto) {
    	AdminDto response = new AdminDto();
    	Admin admin = new Admin(); 
    	
    	if(userDto.getUuidBeneficiaire()!=null )
		{
			Beneficiaire beneficiaire = beneficiaireRepository.findById(userDto.getUuidBeneficiaire()).orElseThrow(null);
			admin.setBeneficiaire(beneficiaire);
			admin.setEmail(userDto.getEmail());
			beneficiaire.setEmail(userDto.getEmail());
			beneficiaireRepository.save(beneficiaire);
		}
		else 
		{
			admin.setEmail(userDto.getEmail());
		}
    	 
    	// Utiliser le mot de passe fourni ou générer un mot de passe aléatoire
    	String password;
    	if (userDto.getMotDePass() != null && !userDto.getMotDePass().isEmpty()) {
    	    password = userDto.getMotDePass();
    	    logger.info("Utilisation du mot de passe fourni pour {}", userDto.getEmail());
    	} else {
    	    // Mot de passe par défaut conservé pour compatibilité
    	    password = "123456";
    	    logger.info("Utilisation du mot de passe par défaut pour {}", userDto.getEmail());
    	}
    	
    	admin.setMotDePass(passwordEncoder.encode(password));
    	admin.setEnabled(true);
    	admin.setNonExpired(true);
    	admin.setNonLocked(true);
    	admin.setRoles(userDto.getRoles());
    	admin.setFirstConnection(false);
		 
		Admin savedAdmin = adminRepository.save(admin);
		return Mapper.toAdminDto(savedAdmin);
    }

	@Override
	public AdminDto getAdminByEmail(String email) { 
		Optional<Admin> adminOptional = adminRepository.findByEmail(email);
		if (!adminOptional.isPresent()) {
		    logger.error("Aucun admin trouvé avec l'email: {}", email);
		    return null;
		}
		Admin admin = adminOptional.get();
		return Mapper.toAdminDto(admin);
	}
	
	@Override
	public AdminDto updateAdmin(AdminDto userDto, String uuid) {
		Admin admin = adminRepository.findById(uuid).orElseThrow(null);
		
	   	if(userDto.getUuidBeneficiaire()!=null)
		{
			Beneficiaire beneficiaire = beneficiaireRepository.findById(userDto.getUuidBeneficiaire()).orElseThrow(null);
			admin.setBeneficiaire(beneficiaire);
		}
		if(userDto.getEmail()!= admin.getEmail()) 
		{
			if(admin.getBeneficiaire()!=null)
			{
				Beneficiaire beneficiaire = beneficiaireRepository.findById(admin.getBeneficiaire().getUuid()).orElseThrow(null);
				beneficiaire.setEmail(userDto.getEmail());
				admin.setBeneficiaire(beneficiaire);
				beneficiaireRepository.save(beneficiaire);
			}
			admin.setEmail(userDto.getEmail());
		}
		if(userDto.getMotDePass()!=null && !userDto.getMotDePass().isEmpty()) {
			admin.setMotDePass(passwordEncoder.encode(userDto.getMotDePass()));
		}
		if(userDto.isEnabled()!=admin.isEnabled()) {
			admin.setEnabled(userDto.isEnabled());
		}
		if(userDto.isNonExpired()!=admin.isNonExpired()) {
			admin.setNonExpired(userDto.isNonExpired());
		}
		if(userDto.isNonLocked()!=admin.isNonLocked()) {
			admin.setNonLocked(userDto.isNonLocked());
		}
		if(userDto.getRoles()!=admin.getRoles()) {
			admin.setRoles(userDto.getRoles());
		}
		 
		Admin savedAdmin = adminRepository.save(admin);
		return Mapper.toAdminDto(savedAdmin);
	}
	
	@Override
	public AdminDto changeEmailAndPassword(String email, String password) {
		Optional<Admin> adminOpt = adminRepository.findByEmail(email);
		if(!adminOpt.isPresent()) {
		    logger.error("Aucun admin trouvé avec l'email: {} pour changer le mot de passe", email);
		    return null;
		}
		
		Admin admin = adminOpt.get();
		// S'assurer que le mot de passe n'est pas vide
		if (password != null && !password.isEmpty()) {
		    admin.setMotDePass(passwordEncoder.encode(password));
		    admin.setFirstConnection(true);
		    
		    Admin saveAdmin = adminRepository.save(admin);
		    return Mapper.toAdminDto(saveAdmin);
		} else {
		    logger.error("Tentative de changement de mot de passe avec une valeur vide pour {}", email);
		    return null;
		}
	}

	@Override
	public void deleteEntity(String uuid) {
		adminRepository.deleteById(uuid);
	}

	@Override
	public AdminDto findById(String uuid) {
		Admin admin = adminRepository.findById(uuid).orElseThrow(null);
		return Mapper.toAdminDto(admin);
	}

	@Override
	public List<AdminDto> getByUserOnLigne() {
		List<Admin> liste = adminRepository.getByUserOnLigne(); 
		final List<AdminDto> listeDto = new ArrayList<>();
		liste.forEach(el -> listeDto.add(Mapper.toAdminDto(el)));
		return listeDto;
	}

	@Override
	public List<AdminDto> findAll() {
		List<Admin> liste = adminRepository.findAll();
		final List<AdminDto> listeDto = new ArrayList<AdminDto>();
		liste.forEach(el -> listeDto.add(Mapper.toAdminDto(el)));
		return listeDto;
	}

	@Override
	public void deconnecterAdmin() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getName() != null) {
		    Optional<Admin> adminOpt = adminRepository.findByEmail(auth.getName());
		    if (adminOpt.isPresent()) {
		        Admin admin = adminOpt.get();
		        admin.setOnline(false);
		        adminRepository.save(admin);
		    }
		}
	}

	// Renommé pour la clarté
	@Override
	public AdminDto updatePassword(String email, String newPassword) {
		Optional<Admin> adminOpt = adminRepository.findByEmail(email);
		if(!adminOpt.isPresent()) {
		    logger.error("Aucun admin trouvé avec l'email: {} pour mise à jour du mot de passe", email);
		    return null;
		}
		
		Admin admin = adminOpt.get();
		// S'assurer que le mot de passe n'est pas vide
		if (newPassword != null && !newPassword.isEmpty()) {
		    admin.setMotDePass(passwordEncoder.encode(newPassword));
		    Admin adminSave = adminRepository.save(admin);
		    return Mapper.toAdminDto(adminSave);
		} else {
		    logger.error("Tentative de mise à jour du mot de passe avec une valeur vide pour {}", email);
		    return null;
		}
	}
	
	// Méthode pour compatibilité avec votre code existant
	@Override
	public AdminDto getMotDePass(String email) {
	    return updatePassword(email, "123456"); // Utilise la nouvelle méthode et définit un mot de passe par défaut
	}
}