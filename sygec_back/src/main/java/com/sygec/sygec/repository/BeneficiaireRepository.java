package com.sygec.sygec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sygec.sygec.model.Beneficiaire;


public interface BeneficiaireRepository  extends JpaRepository<Beneficiaire, String> {
	
	

//	@Query(value="select b from Beneficiaire b where b.Email = :username And b.MotDePass = :password")
//	Optional<Beneficiaire> findByEmailAndMotDePasse(@Param("username") String username, @Param("password") String password); 
	

}
 