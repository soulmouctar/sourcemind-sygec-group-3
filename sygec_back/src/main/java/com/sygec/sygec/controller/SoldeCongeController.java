package com.sygec.sygec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sygec.sygec.dto.SoldeCongeDto;
import com.sygec.sygec.service.SoldeCongeService;

@RestController
@CrossOrigin
public class SoldeCongeController {
	
	@Autowired
	private SoldeCongeService soldeCongeService;
	
	@PostMapping("/SoldeConge/save")
	public SoldeCongeDto save(@RequestBody SoldeCongeDto soldeCongeDto) {
		return soldeCongeService.save(soldeCongeDto);
	}
	
	@PutMapping("/SoldeConge/update/{uuid}")
	public SoldeCongeDto updateClasse(@RequestBody  SoldeCongeDto soldeCongeDto,@PathVariable String uuid) {
		return soldeCongeService.update(soldeCongeDto, uuid);
	}
	
	@GetMapping("SoldeConge/id/{uuid}")
	public SoldeCongeDto findById(@PathVariable String uuid) {
		return soldeCongeService.findById(uuid);
	}
	
	@GetMapping("/SoldeConge/all")
	public List<SoldeCongeDto> findAll(){
		return soldeCongeService.findAll();
	}
		
	@DeleteMapping("/SoldeConge/delete/{uuid}")	
	void deleteEntity(@PathVariable String uuid) {
		soldeCongeService.delete(uuid);
	}
	
	  @GetMapping("/soldes-beneficiaire/{beneficiaireUuid}")
	    public ResponseEntity<List<SoldeCongeDto>> findByBeneficiaire(
	            @PathVariable String beneficiaireUuid) {
	        return ResponseEntity.ok(soldeCongeService.findByBeneficiaire(beneficiaireUuid));
	    }

	    @GetMapping("/annee/{annee}")
	    public ResponseEntity<List<SoldeCongeDto>> findByAnnee(@PathVariable Integer annee) {
	        return ResponseEntity.ok(soldeCongeService.findByAnnee(annee));
	    }

	    @PutMapping("SoldeConge/{uuid}/nombreJours")
	    public ResponseEntity<Void> deduireSolde(@PathVariable String uuid, 
	                                           @RequestParam float nombreJours) {
	        soldeCongeService.deduireSolde(uuid, nombreJours);
	        return ResponseEntity.ok().build();
	    }
	    
	    

}
