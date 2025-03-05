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

import com.sygec.sygec.dto.TypeCongeDto;
import com.sygec.sygec.service.TypeCongeService;



@RestController
@CrossOrigin
public class TypeCongeController {
	
	
	@Autowired
	private TypeCongeService typeCongeService;
	
	@PostMapping("/TypeConge/save")
	public TypeCongeDto save(@RequestBody TypeCongeDto typeCongeDto) {
		return typeCongeService.save(typeCongeDto);
	}
	
	@PutMapping("/TypeConge/update/{uuid}")
	public TypeCongeDto updateClasse(@RequestBody  TypeCongeDto typeCongeDto,@PathVariable String uuid) {
		return typeCongeService.update(typeCongeDto, uuid);
	}
	
	@GetMapping("/TypeConge/{uuid}")
	public TypeCongeDto findById(@PathVariable String uuid) {
		return typeCongeService.findById(uuid);
	}
	
	@GetMapping("/TypeConge/all")
	public List<TypeCongeDto> findAll(){
		return typeCongeService.findAll();
	}
		
	@DeleteMapping("/TypeConge/delete/{uuid}")	
	void deleteEntity(@PathVariable String uuid) {
		typeCongeService.delete(uuid);
	}


}
