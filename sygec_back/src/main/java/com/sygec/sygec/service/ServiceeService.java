package com.sygec.sygec.service;

import java.util.List;

import com.sygec.sygec.dto.ServiceeDto;



public interface ServiceeService {

	ServiceeDto save(ServiceeDto serviceeDto);
	ServiceeDto update(ServiceeDto serviceeDto, String uuid);
	ServiceeDto findById(String uuid);
	List<ServiceeDto> findAll();
	void delete(String uuid);
}
