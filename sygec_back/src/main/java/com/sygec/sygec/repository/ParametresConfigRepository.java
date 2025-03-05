package com.sygec.sygec.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sygec.sygec.model.ParametresConfig;

public interface ParametresConfigRepository extends JpaRepository<ParametresConfig, String> {
	  /**
     * Récupère les paramètres de configuration actifs
     */
    @Query("SELECT p FROM ParametresConfig p WHERE p.actif = true ORDER BY p.uuid DESC")
    Optional<ParametresConfig> findActifParametres();

}
