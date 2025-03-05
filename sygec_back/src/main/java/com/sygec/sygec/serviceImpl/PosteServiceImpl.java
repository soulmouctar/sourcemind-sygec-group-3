package com.sygec.sygec.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sygec.sygec.dto.PosteDto;
import com.sygec.sygec.dto.ServiceeDto;
import com.sygec.sygec.mapper.Mapper;
import com.sygec.sygec.model.Poste;
import com.sygec.sygec.model.Servicee;
import com.sygec.sygec.repository.PosteRepository;
import com.sygec.sygec.repository.ServiceeRepository;
import com.sygec.sygec.service.PosteService;

import ch.qos.logback.classic.Logger;

@Service
public class PosteServiceImpl implements PosteService{
	
	@Autowired
	PosteRepository posteRepository;
	
	@Autowired
	ServiceeRepository serviceeRepository;
	
	Logger logger = (Logger) LoggerFactory.getLogger(PosteServiceImpl.class);

	@Override
	public PosteDto save(PosteDto posteDto) {
		Poste poste = new Poste();
		Servicee servicee = serviceeRepository.findById(posteDto.getServicee_uuid()).orElseThrow(null);
		poste.setUuid(posteDto.getUuid());
		poste.setLibelle(posteDto.getLibelle());
		poste.setDescription(posteDto.getDescription());
		poste.setServicee(servicee);
		Poste posteSave = posteRepository.save(poste);
		return Mapper.toClasseDto(posteSave);
	}

	@Override
	public PosteDto update(PosteDto posteDto, String uuid) {
		Poste poste = posteRepository.findById(uuid).orElseThrow(null);
		Servicee servicee = serviceeRepository.findById(posteDto.getServicee_uuid()).orElseThrow(null);
		poste.setUuid(posteDto.getUuid());
		poste.setLibelle(posteDto.getLibelle());
		poste.setDescription(posteDto.getDescription());
		poste.setServicee(servicee);
		Poste posteSave = posteRepository.save(poste);
		return Mapper.toClasseDto(posteSave);
	}

	@Override
	public PosteDto findById(String uuid) {
		if (uuid == null) {
			logger.info("l'ID est null");
			return null;
		}
		Poste poste = posteRepository.findById(uuid).orElseThrow(null);
		return Mapper.toClasseDto(poste);
	}

	@Override
	public List<PosteDto> findAll() {
		List<Poste> postes = posteRepository.findAll();
		List<PosteDto> posteDtos = new ArrayList<PosteDto>();
		postes.forEach(poste -> posteDtos.add(Mapper.toClasseDto(poste)));
		return posteDtos;
	}

	@Override
	public void delete(String uuid) {
		if (uuid == null) {
			logger.info("l'ID n'existe pas");
			return;
		}
		posteRepository.deleteById(uuid);
	}

	@Override
	public List<PosteDto> findAllPosteByServicee(String uuid_servicee) {
			// TODO Auto-generated method stub
			List<Poste> postes = posteRepository.findAllPosteByService(uuid_servicee);
			List<PosteDto> posteDtos = new ArrayList<PosteDto>();
			postes.forEach(poste -> posteDtos.add(Mapper.toClasseDto(poste)));
			return posteDtos;
	}
		
	





}
