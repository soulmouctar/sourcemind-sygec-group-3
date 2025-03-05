package com.sygec.sygec.service;

import java.util.List;

import com.sygec.sygec.dto.SoldeCongeDto;

public interface SoldeCongeService {

	SoldeCongeDto save(SoldeCongeDto soldeCongeDto);
	SoldeCongeDto update(SoldeCongeDto soldeCongeDto, String uuid);
	SoldeCongeDto findById(String uuid);
	List<SoldeCongeDto> findAll();
	void delete(String uuid);
	List<SoldeCongeDto> findByBeneficiaire(String beneficiaireUuid);
    List<SoldeCongeDto> findByAnnee(Integer annee);
    void deduireSolde(String uuid, float nombreJours);
}
