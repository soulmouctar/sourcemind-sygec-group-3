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
import org.springframework.web.bind.annotation.RestController;

import com.sygec.sygec.dto.ServiceeDto;
import com.sygec.sygec.model.Servicee;
import com.sygec.sygec.service.ServiceeService;


@RestController
@CrossOrigin
public class ServiceeController {
	
	@Autowired
	private ServiceeService serviceeService;
	
	@PostMapping("/Service/save")
	public ServiceeDto save(@RequestBody ServiceeDto serviceeDto) {
		return serviceeService.save(serviceeDto);
	}
	
	@PutMapping("/Service/update/{uuid}")
	public ServiceeDto updateClasse(@RequestBody  ServiceeDto serviceeDto,@PathVariable String uuid) {
		return serviceeService.update(serviceeDto, uuid);
	}
	
	@GetMapping("/Service/{uuid}")
	public ServiceeDto findById(@PathVariable String uuid) {
		return serviceeService.findById(uuid);
	}
	
	@GetMapping("/Service/all")
	public List<ServiceeDto> findAll(){
		return serviceeService.findAll();
	}
		
	@DeleteMapping("/Service/delete/{uuid}")	
	void deleteEntity(@PathVariable String uuid) {
		serviceeService.delete(uuid);
	}
	




}
