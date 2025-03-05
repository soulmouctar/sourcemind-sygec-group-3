package com.sygec.sygec.controller;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.sygec.sygec.dto.DemandeCongeDto;
import com.sygec.sygec.dto.Value;
import com.sygec.sygec.repository.DemandeCongeRepository;
import com.sygec.sygec.service.DemandeCongeService;
import com.sygec.sygec.serviceImpl.BeneficiaireServiceImpl;
import com.sygec.sygec.serviceImpl.DemandeCongeServiceImpl;


@RestController
@CrossOrigin
public class DemandeCongeController {
	
	@Autowired
	private DemandeCongeService demandeCongeService;
	
	@Autowired
	private DemandeCongeRepository demandeCongeRepository;
	
	@Autowired
	private DemandeCongeServiceImpl demandeCongeServiceImpl;
	
	Logger logger = LoggerFactory.getLogger(DemandeCongeController.class);
	
	@PostMapping("/DemandeConge/save")
	public DemandeCongeDto save(@RequestBody DemandeCongeDto demandeCongeDto) {
		return demandeCongeService.save(demandeCongeDto);
	}
	
	@PutMapping("/DemandeConge/update/{uuid}")
	public DemandeCongeDto updateClasse(@RequestBody  DemandeCongeDto demandeCongeDto,@PathVariable String uuid) {
		return demandeCongeService.update(demandeCongeDto, uuid);
	}
	
	@GetMapping("/DemandeConge/{uuid}")
	public DemandeCongeDto findById(@PathVariable String uuid) {
		return demandeCongeService.findById(uuid);
	}
	
	@GetMapping("/DemandeConge/all")
	public List<DemandeCongeDto> findAll(){
		return demandeCongeService.findAll();
	}
	
	
	@GetMapping("/DemandeConge/findAllIsValider")
	public List<DemandeCongeDto> findAllIsValider(){
		return demandeCongeService.findAllIsValider();
	}
	
	@GetMapping("/DemandeConge/findAllIsNotValider")
	public List<DemandeCongeDto> findAllIsNotValider(){
		return demandeCongeService.findAllIsNotValider();
	}
	
	@GetMapping("/DemandeConge/findAllIsRejetter")
	public List<DemandeCongeDto> findAllIsRejetter(){
		return demandeCongeService.findAllIsRejetter();
	}
	
	@GetMapping("/DemandeConge/findAllIsAnnuler")
	public List<DemandeCongeDto> findAllIsAnnuler(){
		return demandeCongeService.findAllIsAnnuler();
	}
		
	@DeleteMapping("/DemandeConge/delete/{uuid}")	
	void deleteEntity(@PathVariable String uuid) {
		demandeCongeService.delete(uuid);
	}
	
	/**
     * Récupère toutes les demandes de congé d'un bénéficiaire
     */
    @GetMapping("/beneficiaire/{uuidBeneficiaire}")
    public ResponseEntity<List<DemandeCongeDto>> getDemandeCongesByBeneficiaire(
            @PathVariable String uuidBeneficiaire) {
        List<DemandeCongeDto> demandes = demandeCongeService.findAllByBeneficiaire(uuidBeneficiaire);
        return ResponseEntity.ok(demandes);
    }
	
//	@GetMapping("/genererCodeDemandeBeneficiaire")
//    public Value generateNextCode() {
//        // Algorithme de récupération automatique des deux derniers chiffres de l'année en cours
//        Calendar calendrier = Calendar.getInstance();
//        int anneeActuelle = calendrier.get(Calendar.YEAR);
//        int deuxDerniersChiffres = anneeActuelle % 100;
//
//        Value resultat = new Value();
//        // Récupération du dernier code de l'adhérent
//        String lastCode = demandeCongeRepository.dermiereValeur();
//
//        if (lastCode == null) {
//            resultat.setValue("D000001/" + deuxDerniersChiffres);
//        } else {
//            // Utilisation d'un regex pour l'extraction de la partie numérique
//            Pattern pattern = Pattern.compile("\\d+");
//            Matcher matcher = pattern.matcher(lastCode);
//            String partieExtraite = "";
//
//            // Vérification de la correspondance
//            if (matcher.find()) {
//                partieExtraite = matcher.group();
//            }
//
//            int lastNumber = Integer.parseInt(partieExtraite);
//            lastNumber++;
//            String nouvelleChaine = String.format("%06d", lastNumber);
//            String newCode = "D" + nouvelleChaine + "/" + deuxDerniersChiffres;
//            resultat.setValue(newCode);
//        }
//
//        return resultat;
//    }
//	
	
//	@GetMapping("/genererCodeDemandeBeneficiaire")
//	public Value generateNextCode() {
//	    try {
//	        // Récupération de l'année courante
//	        Calendar calendrier = Calendar.getInstance();
//	        int anneeActuelle = calendrier.get(Calendar.YEAR);
//	        int deuxDerniersChiffres = anneeActuelle % 100;
//
//	        Value resultat = new Value();
//	        // Récupération du dernier code
//	        String lastCode = demandeCongeRepository.dermiereValeur();
//
//	        if (lastCode == null || lastCode.trim().isEmpty()) {
//	            // Premier code de l'année
//	            resultat.setValue("D000001/" + deuxDerniersChiffres);
//	            return resultat;
//	        }
//
//	        // Extraction du numéro avec regex
//	        Pattern pattern = Pattern.compile("D(\\d{6})/\\d{2}");
//	        Matcher matcher = pattern.matcher(lastCode);
//
//	        if (!matcher.matches()) {
//	            // Si le format ne correspond pas, on commence à 1
//	            resultat.setValue("D000001/" + deuxDerniersChiffres);
//	            return resultat;
//	        }
//
//	        // Extraction de la partie numérique
//	        String partieNumerique = matcher.group(1);
//	        
//	        try {
//	            int lastNumber = Integer.parseInt(partieNumerique);
//	            lastNumber++;
//	            
//	            // Vérification du dépassement
//	            if (lastNumber > 999999) {
//	                throw new IllegalStateException("Dépassement de la limite maximale du compteur");
//	            }
//	            
//	            // Formatage avec padding de zéros
//	            String nouvelleChaine = String.format("D%06d/%02d", lastNumber, deuxDerniersChiffres);
//	            resultat.setValue(nouvelleChaine);
//	            
//	            return resultat;
//	            
//	        } catch (NumberFormatException e) {
//	            // En cas d'erreur de parsing, on recommence à 1
//	            resultat.setValue("D000001/" + deuxDerniersChiffres);
//	            return resultat;
//	        }
//	        
//	    } catch (Exception e) {
//	        // Log de l'erreur
//	        logger.error("Erreur lors de la génération du code : " + e.getMessage(), e);
//	        throw new RuntimeException("Erreur lors de la génération du code", e);
//	    }
//	}
	
	@GetMapping("/genererCodeDemandeBeneficiaire")
	public Value generateNextCode() {
	    try {
	        Calendar calendrier = Calendar.getInstance();
	        int deuxDerniersChiffres = calendrier.get(Calendar.YEAR) % 100;

	        Value resultat = new Value();
	        String lastCode = demandeCongeRepository.dermiereValeur();

	        if (lastCode == null || lastCode.trim().isEmpty()) {
	            resultat.setValue("D000001/" + deuxDerniersChiffres);
	            return resultat;
	        }

	        // Le reste de votre code reste identique...
	        
	    } catch (Exception e) {
	        logger.error("Erreur lors de la génération du code : " + e.getMessage(), e);
	        throw new RuntimeException("Erreur lors de la génération du code", e);
	    }
		return null;
	}
	

}
