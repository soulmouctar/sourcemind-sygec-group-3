package com.sygec.sygec.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sygec.sygec.dto.TypeCongeDto;
import com.sygec.sygec.mapper.Mapper;
import com.sygec.sygec.model.TypeConge;
import com.sygec.sygec.repository.TypeCongeRepository;
import com.sygec.sygec.service.TypeCongeService;

import ch.qos.logback.classic.Logger;

@Service
public class TypeCongeServiceImpl implements TypeCongeService {
	
	@Autowired
	TypeCongeRepository typeCongeRepository;
	
	Logger logger = (Logger) LoggerFactory.getLogger(SoldeCongeServiceImpl.class);

	@Override
	public TypeCongeDto save(TypeCongeDto typeCongeDto) {
		TypeConge typeConge = new TypeConge();
		
		typeConge.setUuid(typeCongeDto.getUuid());
		typeConge.setLibelle(typeCongeDto.getLibelle());
		typeConge.setDeductible(typeCongeDto.isDeductible());
		typeConge.setDescription(typeCongeDto.getDescription());
		TypeConge typeCongeSave = typeCongeRepository.save(typeConge);
		return Mapper.toClasseDto(typeCongeSave);
	}

	@Override
	public TypeCongeDto update(TypeCongeDto typeCongeDto, String uuid) {
		TypeConge typeConge = typeCongeRepository.findById(uuid).orElseThrow(null);
		
		typeConge.setUuid(typeCongeDto.getUuid());
		typeConge.setLibelle(typeCongeDto.getLibelle());
		typeConge.setDeductible(typeCongeDto.isDeductible());
		typeConge.setDescription(typeCongeDto.getDescription());
		TypeConge typeCongeSave = typeCongeRepository.save(typeConge);
		return Mapper.toClasseDto(typeCongeSave);
	}

	@Override
	public TypeCongeDto findById(String uuid) {
		if (uuid == null) {
			logger.info("l'ID est null");
			return null;
		}
		TypeConge typeConge = typeCongeRepository.findById(uuid).orElseThrow(null);
		return Mapper.toClasseDto(typeConge);
	}

	@Override
	public List<TypeCongeDto> findAll() {
		List<TypeConge> typeConges = typeCongeRepository.findAll();
		List<TypeCongeDto> typeCongeDtos = new ArrayList<TypeCongeDto>();
		typeConges.forEach(typeConge -> typeCongeDtos.add(Mapper.toClasseDto(typeConge)));
		return typeCongeDtos;
	}

	@Override
	public void delete(String uuid) {
		if (uuid == null) {
			logger.info("l'ID n'existe pas");
			return;
		}
		typeCongeRepository.deleteById(uuid);
	}

	
}
