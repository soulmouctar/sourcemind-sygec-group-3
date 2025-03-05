package com.sygec.sygec.service;

import java.util.List;
import com.sygec.sygec.dto.DemandeCongeDto;

public interface DemandeCongeService {
	
	DemandeCongeDto save(DemandeCongeDto demandeCongeDto);
	DemandeCongeDto update(DemandeCongeDto demandeCongeDto, String uuid);
	DemandeCongeDto findById(String uuid);
	List<DemandeCongeDto> findAll();
	List<DemandeCongeDto> findAllByBeneficiaire(String uuidBeneficiaire);
	void delete(String uuid);
	List<DemandeCongeDto> findAllIsValider();
	List<DemandeCongeDto> findAllIsNotValider();
	List<DemandeCongeDto> findAllIsRejetter();
	List<DemandeCongeDto> findAllIsAnnuler();


}
