package com.sygec.sygec.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import com.sygec.sygec.enume.StatutDemande;
import com.sygec.sygec.model.Beneficiaire;
import com.sygec.sygec.model.DemandeConge;
import javax.persistence.QueryHint; 

public interface DemandeCongeRepository extends JpaRepository<DemandeConge, String>{
	
	@Query(value="select d from DemandeConge d where d.valider=true and d.rejetter=false and d.annuler=false")
	List<DemandeConge> findAllIsValider();

	@Query(value="select d from DemandeConge d where d.valider=false and d.rejetter=false and d.annuler=false")
	List<DemandeConge> findAllIsNotValider(); 
	
	@Query(value="select d from DemandeConge d where d.valider=false and d.rejetter=true and d.annuler=false")
	List<DemandeConge> findAllIsRejetter(); 
	
	@Query(value="select d from DemandeConge d where d.valider=false and d.rejetter=false and d.annuler=true")
	List<DemandeConge> findAllIsAnnuler(); 
	
	@Query(value="SELECT a.code FROM demande_conge a ORDER BY a.code DESC limit 1", nativeQuery = true)
    String dermiereValeur();
	
	long countByBeneficiaire(Beneficiaire beneficiaire);

//	 @QueryHints(value = { @QueryHint(name = "org.hibernate.cacheable", value = "true") })
//	    @Query("SELECT a.code FROM DemandeConge a ORDER BY a.code DESC")
//	    Optional<String> findLastCode();
	
	/**
     * Trouve toutes les demandes de congé d'un bénéficiaire
     * 
     * @param beneficiaire le bénéficiaire
     * @return la liste des demandes de congé
     */
    List<DemandeConge> findByBeneficiaire(Beneficiaire beneficiaire);
    
    /**
     * Trouve toutes les demandes de congé d'un bénéficiaire avec un statut spécifique
     * 
     * @param beneficiaire le bénéficiaire
     * @param statut le statut de la demande
     * @return la liste des demandes de congé
     */
    List<DemandeConge> findByBeneficiaireAndStatut(Beneficiaire beneficiaire, StatutDemande statut);
    
    /**
     * Trouve toutes les demandes de congé validées pour un bénéficiaire
     * 
     * @param beneficiaire le bénéficiaire
     * @return la liste des demandes de congé validées
     */
    List<DemandeConge> findByBeneficiaireAndValiderTrue(Beneficiaire beneficiaire);
    
    /**
     * Trouve toutes les demandes de congé en cours (qui se chevauchent avec la période spécifiée)
     * 
     * @param dateDebut la date de début de la période
     * @param dateFin la date de fin de la période
     * @return la liste des demandes de congé en cours
     */
    @Query("SELECT d FROM DemandeConge d WHERE d.valider = true AND NOT(d.dateFin < :dateDebut OR d.dateDebut > :dateFin)")
    List<DemandeConge> findOverlappingValidatedRequests(@Param("dateDebut") String dateDebut, @Param("dateFin") String dateFin);
    
    /**
     * Compte le nombre de demandes de congé validées pour un bénéficiaire dans une année spécifique
     * 
     * @param beneficiaireUuid l'identifiant du bénéficiaire
     * @param annee l'année des demandes
     * @return le nombre de demandes
     */
    @Query("SELECT COUNT(d) FROM DemandeConge d WHERE d.beneficiaire.uuid = :beneficiaireUuid AND d.valider = true AND d.dateDebut LIKE :annee%")
    Long countValidatedRequestsByBeneficiaireAndYear(@Param("beneficiaireUuid") String beneficiaireUuid, @Param("annee") String annee);
    
    
}
