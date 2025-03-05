package com.sygec.sygec.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sygec.sygec.model.Beneficiaire;
import com.sygec.sygec.model.SoldeConge;

public interface SoldeCongeRepository extends JpaRepository<SoldeConge, String>{
	  boolean existsByBeneficiaireAndAnnee(Beneficiaire beneficiaire, Integer annee);
	    List<SoldeConge> findByBeneficiaireUuid(String beneficiaireUuid);
	    List<SoldeConge> findByAnnee(Integer annee);
	    
	    
	    /**
	     * Trouve le solde de congé d'un bénéficiaire pour une année spécifique
	     * 
	     * @param beneficiaire le bénéficiaire
	     * @param annee l'année du solde
	     * @return le solde de congé si trouvé
	     */
	    Optional<SoldeConge> findByBeneficiaireAndAnnee(Beneficiaire beneficiaire, int annee);
	    
	    /**
	     * Trouve tous les soldes de congé d'un bénéficiaire
	     * 
	     * @param beneficiaire le bénéficiaire
	     * @return la liste des soldes de congé
	     */
	    List<SoldeConge> findByBeneficiaire(Beneficiaire beneficiaire);
	    
	    /**
	     * Trouve tous les soldes de congé pour une année spécifique
	     * 
	     * @param annee l'année des soldes
	     * @return la liste des soldes de congé
	     */
	    List<SoldeConge> findByAnnee(int annee);
	    
	    /**
	     * Trouve le solde de congé d'un bénéficiaire pour l'année en cours
	     * 
	     * @param beneficiaireUuid l'identifiant du bénéficiaire
	     * @param annee l'année en cours
	     * @return le solde de congé si trouvé
	     */
	    @Query("SELECT s FROM SoldeConge s WHERE s.beneficiaire.uuid = :beneficiaireUuid AND s.annee = :annee")
	    Optional<SoldeConge> findByBeneficiaireUuidAndAnnee(@Param("beneficiaireUuid") String beneficiaireUuid, @Param("annee") int annee);
	    
	    /**
	     * Trouve tous les soldes de congé avec des jours restants supérieurs à zéro
	     * 
	     * @return la liste des soldes de congé actifs
	     */
	    @Query("SELECT s FROM SoldeConge s WHERE s.soldeRestandt > 0")
	    List<SoldeConge> findAllWithRemainingDays();
	    
	    /**
	     * Trouve tous les soldes de congé pour une liste de bénéficiaires
	     * 
	     * @param beneficiaireUuids liste des identifiants des bénéficiaires
	     * @param annee l'année des soldes
	     * @return la liste des soldes de congé
	     */
	    @Query("SELECT s FROM SoldeConge s WHERE s.beneficiaire.uuid IN :beneficiaireUuids AND s.annee = :annee")
	    List<SoldeConge> findByBeneficiaireUuidsAndAnnee(@Param("beneficiaireUuids") List<String> beneficiaireUuids, @Param("annee") int annee);

}
