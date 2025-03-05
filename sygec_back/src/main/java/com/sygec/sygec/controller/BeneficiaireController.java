package com.sygec.sygec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sygec.sygec.dto.BeneficiaireDto;

import com.sygec.sygec.service.BeneficiaireService;



@RestController
@CrossOrigin
public class BeneficiaireController {
	
	@Autowired
	private BeneficiaireService beneficiaireService;
	
	@PostMapping("/Beneficiaire/save")
	public BeneficiaireDto save(@RequestBody BeneficiaireDto beneficiaireDto) {
		return beneficiaireService.save(beneficiaireDto);
	}
	
	@PutMapping("/Beneficiaire/update/{uuid}")
	public BeneficiaireDto updateClasse(@RequestBody  BeneficiaireDto beneficiaireDto,@PathVariable String uuid) {
		return beneficiaireService.updateClasse(beneficiaireDto, uuid);
	}
	
	@GetMapping("/Beneficiaire/{uuid}")
	public BeneficiaireDto findById(@PathVariable String uuid) {
		return beneficiaireService.findById(uuid);
	}
	
	@GetMapping("/Beneficiaire/all")
	public List<BeneficiaireDto> findAll(){
		return beneficiaireService.findAll();
	}
		
	@DeleteMapping("/Beneficiaire/delete/{uuid}")	
	void deleteEntity(@PathVariable String uuid) {
		beneficiaireService.deleteEntity(uuid);
	}
	
	

}
