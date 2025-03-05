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

import com.sygec.sygec.dto.PosteDto;
import com.sygec.sygec.dto.ServiceeDto;
import com.sygec.sygec.service.PosteService;


@RestController
@CrossOrigin
public class PosteController {
	
	@Autowired
	private PosteService posteService;
	
	@PostMapping("/Poste/save")
	public PosteDto save(@RequestBody PosteDto posteDto) {
		return posteService.save(posteDto);
	}
	
	@PutMapping("/Poste/update/{uuid}")
	public PosteDto updateClasse(@RequestBody  PosteDto posteDto,@PathVariable String uuid) {
		return posteService.update(posteDto, uuid);
	}
	
	@GetMapping("/Poste/{uuid}")
	public PosteDto findById(@PathVariable String uuid) {
		return posteService.findById(uuid);
	}
	
	@GetMapping("/Poste/all")
	public List<PosteDto> findAll(){
		return posteService.findAll();
	}
		
	@DeleteMapping("/Poste/delete/{uuid}")	
	void deleteEntity(@PathVariable String uuid) {
		posteService.delete(uuid);
	}

	@GetMapping("/Poste/byServicee/{uuid_servicee}")
	public List<PosteDto> findAllPosteByServicee(@PathVariable String uuid_servicee){
		return posteService.findAllPosteByServicee(uuid_servicee);
	}

}
