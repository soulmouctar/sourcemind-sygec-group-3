package com.sygec.sygec.service;

import java.util.List;

import com.sygec.sygec.dto.TypeCongeDto;

public interface TypeCongeService {

	TypeCongeDto save(TypeCongeDto typeCongeDto);
	TypeCongeDto update(TypeCongeDto typeCongeDto, String uuid);
	TypeCongeDto findById(String uuid);
	List<TypeCongeDto> findAll();
	void delete(String uuid);
}
