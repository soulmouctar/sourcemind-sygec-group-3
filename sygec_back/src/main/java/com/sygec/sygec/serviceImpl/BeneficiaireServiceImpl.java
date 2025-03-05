package com.sygec.sygec.serviceImpl;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sygec.sygec.dto.AdminDto;
import com.sygec.sygec.dto.BeneficiaireDto;
import com.sygec.sygec.dto.ServiceeDto;
import com.sygec.sygec.mapper.Mapper;
import com.sygec.sygec.model.Beneficiaire;
import com.sygec.sygec.model.Servicee;
import com.sygec.sygec.model.StoredFile;
import com.sygec.sygec.repository.BeneficiaireRepository;
import com.sygec.sygec.repository.ServiceeRepository;
import com.sygec.sygec.repository.StoredFilesRepository;
import com.sygec.sygec.service.BeneficiaireService;


import java.util.ArrayList;
import java.util.List;


@Service
public class BeneficiaireServiceImpl  implements BeneficiaireService{ 
	
	@Autowired
	private BeneficiaireRepository beneficiaireRepository;
	
	@Autowired
	private ServiceeRepository serviceRepository;
	@Autowired
	private AdminServiceImpl adminServiceImpl;
	
	@Autowired
	StoredFilesRepository storageFileRepository;


	Logger logger = LoggerFactory.getLogger(BeneficiaireServiceImpl.class);
	
	@Override
	public BeneficiaireDto save(BeneficiaireDto beneficiaireDto) {
		
		Beneficiaire beneficiaire = new Beneficiaire();
		
		StoredFile imgArticle = storageFileRepository.findByUuid(beneficiaireDto.getUuidimgArcticle());
		if (imgArticle == null) {
		    logger.error("L'image avec l'UUID {} n'existe pas dans la base de données.", beneficiaireDto.getUuidimgArcticle());
		    throw new RuntimeException("Image non trouvée");
		}
		beneficiaire.setImgArcticle(imgArticle);
		
		Servicee service = serviceRepository.getById(beneficiaireDto.getService_uuid());
		beneficiaire.setServicee(service);
		
		beneficiaire.setUuid(beneficiaireDto.getUuid());
		beneficiaire.setNom(beneficiaireDto.getNom());
		beneficiaire.setPrenom(beneficiaireDto.getPrenom());
		beneficiaire.setGrade(beneficiaireDto.getGrade());
		beneficiaire.setPhoto(beneficiaireDto.getPhoto());
		beneficiaire.setEmail(beneficiaireDto.getEmail());
		beneficiaire.setPhone(beneficiaireDto.getPhone());
		beneficiaire.setMatricule(beneficiaireDto.getMatricule());
		StoredFile imgArticle1 = storageFileRepository.findByUuid(beneficiaireDto.getUuidimgArcticle());
		beneficiaire.setImgArcticle(imgArticle1);
		Beneficiaire beneficiaireSave = beneficiaireRepository.save(beneficiaire);
		AdminDto adminDto = Mapper.toUser(beneficiaireDto);
		adminDto.setUuidBeneficiaire(beneficiaireSave.getUuid());
		adminServiceImpl.save(adminDto);
		return Mapper.toClasseDto(beneficiaireSave);
	}

	@Override
	public BeneficiaireDto updateClasse(BeneficiaireDto beneficiaireDto, String uuid) {
		Beneficiaire beneficiaire = beneficiaireRepository.findById(uuid).orElseThrow(null);
		
		Servicee servicee = serviceRepository.getById(beneficiaireDto.getService_uuid());
		beneficiaire.setServicee(servicee);
		
		//beneficiaire.setUuid(beneficiaireDto.getUuid());
		beneficiaire.setNom(beneficiaireDto.getNom());
		beneficiaire.setPhone(beneficiaireDto.getPhone());
		beneficiaire.setMatricule(beneficiaireDto.getMatricule());
		StoredFile imgArticle = storageFileRepository.findByUuid(beneficiaireDto.getUuidimgArcticle());
		beneficiaire.setImgArcticle(imgArticle);
		beneficiaire.setPrenom(beneficiaireDto.getPrenom());
		beneficiaire.setGrade(beneficiaireDto.getGrade());
		beneficiaire.setEmail(beneficiaireDto.getEmail());
//		beneficiaire.setMotDePass(beneficiaireDto.getMotDePass());
		Beneficiaire beneficiaireSave = beneficiaireRepository.save(beneficiaire);
		return Mapper.toClasseDto(beneficiaireSave);
	}

	@Override
	public BeneficiaireDto findById(String uuid) {
		if(uuid == null) {
			logger.info("l'ID est null");
			return null;
		}
		// TODO Auto-generated method stub
		Beneficiaire beneficiaire = beneficiaireRepository.findById(uuid).orElseThrow(null);
		return Mapper.toClasseDto(beneficiaire);
	}

	@Override
	public List<BeneficiaireDto> findAll() {
		// TODO Auto-generated method stub
		List<Beneficiaire> beneficiaires = beneficiaireRepository.findAll();
		List<BeneficiaireDto> beneficiaireDtos = new ArrayList<BeneficiaireDto>();
		beneficiaires.forEach(beneficiaire -> beneficiaireDtos.add(Mapper.toClasseDto(beneficiaire)));
		return beneficiaireDtos;
	}

	@Override
	public void deleteEntity(String uuid) {
		if(uuid == null) {
			logger.info("l'ID est null");
			return;
		}
		// TODO Auto-generated method stub
		beneficiaireRepository.deleteById(uuid);
	}

	
	

}
 