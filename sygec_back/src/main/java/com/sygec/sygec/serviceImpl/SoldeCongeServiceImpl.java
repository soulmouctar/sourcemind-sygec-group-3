package com.sygec.sygec.serviceImpl;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sygec.sygec.dto.SoldeCongeDto;
import com.sygec.sygec.mapper.Mapper;
import com.sygec.sygec.model.Beneficiaire;
import com.sygec.sygec.model.SoldeConge;
import com.sygec.sygec.repository.BeneficiaireRepository;
import com.sygec.sygec.repository.SoldeCongeRepository;
import com.sygec.sygec.service.SoldeCongeService;

import ch.qos.logback.classic.Logger;
@Service
public class SoldeCongeServiceImpl implements SoldeCongeService {
	
	@Autowired
	SoldeCongeRepository soldeCongeRepository;
	
	@Autowired
	BeneficiaireRepository beneficiaireRepository;

	Logger logger = (Logger) LoggerFactory.getLogger(SoldeCongeServiceImpl.class);

	@Override
	public SoldeCongeDto save(SoldeCongeDto soldeCongeDto) {
		
		SoldeConge soldeConge = new SoldeConge();
		
		Beneficiaire beneficiaire = beneficiaireRepository.findById(soldeCongeDto.getBeneficiaire_uuid()).orElseThrow(null);
		 if (soldeCongeRepository.existsByBeneficiaireAndAnnee(beneficiaire, soldeCongeDto.getAnnee())) {
	            throw new IllegalStateException("Un solde existe déjà pour cette année");
	        }
		soldeConge.setUuid(soldeCongeDto.getUuid());
		soldeConge.setSoldeAnnuel(soldeCongeDto.getSoldeAnnuel());
		soldeConge.setSoldeRestandt(soldeCongeDto.getSoldeRestandt());
		soldeConge.setAnnee(soldeCongeDto.getAnnee());
		soldeConge.setBeneficiaire(beneficiaire);
		SoldeConge soldeCongeSave = soldeCongeRepository.save(soldeConge);
		return Mapper.toClasseDto(soldeCongeSave);
	}

	@Override
	public SoldeCongeDto update(SoldeCongeDto soldeCongeDto, String uuid) {
		SoldeConge soldeConge = soldeCongeRepository.findById(uuid).orElseThrow(null);
		
		Beneficiaire beneficiaire = beneficiaireRepository.findById(soldeCongeDto.getBeneficiaire_uuid()).orElseThrow(null);
		soldeConge.setUuid(soldeCongeDto.getUuid());
		soldeConge.setSoldeAnnuel(soldeCongeDto.getSoldeAnnuel());
		soldeConge.setSoldeRestandt(soldeCongeDto.getSoldeRestandt());
		soldeConge.setAnnee(soldeCongeDto.getAnnee());
		soldeConge.setBeneficiaire(beneficiaire);
		SoldeConge soldeCongeSave = soldeCongeRepository.save(soldeConge);
		return Mapper.toClasseDto(soldeCongeSave);
	}

	@Override
	public SoldeCongeDto findById(String uuid) {
		if (uuid == null) {
			logger.info("l'ID est null");
			return null;
		}
		SoldeConge soldeConge = soldeCongeRepository.findById(uuid).orElseThrow(null);
		return Mapper.toClasseDto(soldeConge);
	}

	@Override
	public List<SoldeCongeDto> findAll() {
		List<SoldeConge> soldeConges = soldeCongeRepository.findAll();
		List<SoldeCongeDto> soldeCongeDtos = new ArrayList<SoldeCongeDto>();
		soldeConges.forEach(soldeConge -> soldeCongeDtos.add(Mapper.toClasseDto(soldeConge)));
		return soldeCongeDtos;
	}

	@Override
	public void delete(String uuid) {
		if (uuid == null) {
			logger.info("l'ID n'existe pas");
			return;
		}
		soldeCongeRepository.deleteById(uuid);
	}
	
	 @Override
	    public void deduireSolde(String uuid, float nombreJours) {
	        SoldeConge soldeConge = soldeCongeRepository.findById(uuid).orElseThrow(null);

	        if (soldeConge.getSoldeRestandt() < nombreJours) {
	            throw new IllegalStateException("Solde insuffisant");
	        }

	        soldeConge.setSoldeRestandt(soldeConge.getSoldeRestandt() - nombreJours);
	        soldeCongeRepository.save(soldeConge);
	        logger.info("Déduction de {} jours du solde", nombreJours);
	    }
	 
	  @Override
	    public List<SoldeCongeDto> findByBeneficiaire(String beneficiaireUuid) {
	        return soldeCongeRepository.findByBeneficiaireUuid(beneficiaireUuid).stream()
	            .map(Mapper::toClasseDto)
	            .collect(Collectors.toList());
	    }

	    @Override
	    public List<SoldeCongeDto> findByAnnee(Integer annee) {
	        return soldeCongeRepository.findByAnnee(annee).stream()
	            .map(Mapper::toClasseDto)
	            .collect(Collectors.toList());
	    }

}
