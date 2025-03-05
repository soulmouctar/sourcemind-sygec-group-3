package com.sygec.sygec.service;



import com.sygec.sygec.dto.BeneficiaireDto;



import java.util.List;

public interface BeneficiaireService { 
	
	BeneficiaireDto save(BeneficiaireDto beneficiaireDto);
	BeneficiaireDto updateClasse(BeneficiaireDto beneficiaireDto, String uuid);
	BeneficiaireDto findById(String uuid);
	List<BeneficiaireDto> findAll();
	void deleteEntity(String uuid);
	
	

}
 