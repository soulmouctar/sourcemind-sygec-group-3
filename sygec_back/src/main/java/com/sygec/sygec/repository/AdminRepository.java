package com.sygec.sygec.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sygec.sygec.model.Admin;

public interface AdminRepository  extends JpaRepository<Admin, String>{

	   
	    
	    @Query("select u from Admin u where u.Email=:x")
	    Optional<Admin> findByEmail(@Param("x") String Email);
	    
	    @Query("select u from Admin u where u.online=1")
	    public List<Admin> getByUserOnLigne();
	    
	    @Query("select u from Admin u")
	    List<Admin> findAll();
	    
	    Page<Admin> findAll(Pageable pageable);
	    
	    @Query("select p from Admin p where p.supprimer is false and lower(p.Email) like concat('%', :key,'%')")
	    Page<Admin> findAllByKey(Pageable pageable, @Param("key") String key);
	    
	    

	}