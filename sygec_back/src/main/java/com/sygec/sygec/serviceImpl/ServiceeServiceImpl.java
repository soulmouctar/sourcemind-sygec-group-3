package com.sygec.sygec.serviceImpl;



import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sygec.sygec.dto.ServiceeDto;
import com.sygec.sygec.mapper.Mapper;
import com.sygec.sygec.model.Beneficiaire;
import com.sygec.sygec.model.Poste;
import com.sygec.sygec.model.Servicee;
import com.sygec.sygec.repository.BeneficiaireRepository;
import com.sygec.sygec.repository.PosteRepository;
import com.sygec.sygec.repository.ServiceeRepository;
import com.sygec.sygec.service.ServiceeService;

import ch.qos.logback.classic.Logger;


@Service
public class ServiceeServiceImpl implements ServiceeService {
	
	@Autowired
	ServiceeRepository serviceeRepository;
	
	@Autowired
	PosteRepository posteRepository;

	Logger logger = (Logger) LoggerFactory.getLogger(ServiceeServiceImpl.class);

	@Override
	public ServiceeDto save(ServiceeDto serviceeDto) {
		Servicee servicee = new Servicee();
		
		servicee.setUuid(serviceeDto.getUuid());
		servicee.setLibelle(serviceeDto.getLibelle());
		servicee.setDescription(serviceeDto.getDescription());
		serviceeRepository.save(servicee);
		return serviceeDto;
	}

	@Override
	public ServiceeDto update(ServiceeDto serviceeDto, String uuid) {
		Servicee servicee =serviceeRepository.findById(uuid).orElseThrow(null);
		

		servicee.setUuid(serviceeDto.getUuid());
		servicee.setLibelle(serviceeDto.getLibelle());
		servicee.setDescription(serviceeDto.getDescription());

		serviceeRepository.save(servicee);
		return serviceeDto;
	}

	@Override
	public ServiceeDto findById(String uuid) {
		if (uuid == null) {
			logger.info("l'ID est null");
			return null;
		}
		Servicee servicee = serviceeRepository.findById(uuid).orElseThrow(null);
		return Mapper.toClasseDto(servicee);
	}

	@Override
	public List<ServiceeDto> findAll() {
		List<Servicee> servicees = serviceeRepository.findAll();
		List<ServiceeDto> serviceeDtos = new ArrayList<ServiceeDto>();
		servicees.forEach(servicee -> serviceeDtos.add(Mapper.toClasseDto(servicee)));
		return serviceeDtos;
	}

	@Override
	public void delete(String uuid) {
		if (uuid == null) {
			logger.info("l'ID n'existe pas");
			return;
		}
		serviceeRepository.deleteById(uuid);
	}

	
}
