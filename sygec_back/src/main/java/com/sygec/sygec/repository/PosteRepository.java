package com.sygec.sygec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sygec.sygec.model.Poste;
import com.sygec.sygec.model.Servicee;

public interface PosteRepository extends JpaRepository<Poste, String>{
	
	@Query("select r from Poste r where r.servicee.uuid=:x")
	public List<Poste> findAllPosteByService(@Param("x") String uuid);

}
