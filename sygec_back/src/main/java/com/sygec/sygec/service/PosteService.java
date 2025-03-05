package com.sygec.sygec.service;

import java.util.List;

import com.sygec.sygec.dto.PosteDto;
import com.sygec.sygec.dto.ServiceeDto;



public interface PosteService {

	PosteDto save(PosteDto posteDto);
	PosteDto update(PosteDto posteDto, String uuid);
	PosteDto findById(String uuid);
	List<PosteDto> findAll();
	List<PosteDto> findAllPosteByServicee(String uuid_servicee);
	void delete(String uuid);
}
