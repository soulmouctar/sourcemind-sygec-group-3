package com.sygec.sygec.serviceImpl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.sygec.sygec.Api.ApiSms;
import com.sygec.sygec.Api.EmailServiceImpl;
import com.sygec.sygec.dto.DemandeCongeDto;
import com.sygec.sygec.dto.OrangeTokenDto;
import com.sygec.sygec.enume.EnumRole;
import com.sygec.sygec.enume.StatutDemande;
import com.sygec.sygec.exception.CongeException;
import com.sygec.sygec.exceptionGlobale.ResourceNotFoundException;
import com.sygec.sygec.mapper.Mapper;
import com.sygec.sygec.model.Admin;
import com.sygec.sygec.model.Beneficiaire;
import com.sygec.sygec.model.DemandeConge;
import com.sygec.sygec.model.Poste;
import com.sygec.sygec.model.Servicee;
import com.sygec.sygec.model.SoldeConge;
import com.sygec.sygec.model.TypeConge;
import com.sygec.sygec.repository.AdminRepository;
import com.sygec.sygec.repository.BeneficiaireRepository;
import com.sygec.sygec.repository.DemandeCongeRepository;
import com.sygec.sygec.repository.PosteRepository;
import com.sygec.sygec.repository.ServiceeRepository;
import com.sygec.sygec.repository.SoldeCongeRepository;
import com.sygec.sygec.repository.TypeCongeRepository;
import com.sygec.sygec.service.DemandeCongeService;

import ch.qos.logback.classic.Logger;

@Service
@Validated
public class DemandeCongeServiceImpl implements DemandeCongeService {
    
    private static final Logger logger = (Logger) LoggerFactory.getLogger(DemandeCongeServiceImpl.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    // Constantes pour les restrictions
    @Value("${sygec.conge.duree.min:1}")
    private int DUREE_MIN_CONGE;
    
    @Value("${sygec.conge.duree.max:90}")
    private int DUREE_MAX_CONGE;
    
    @Value("${sygec.conge.delai.demande.min:7}")
    private int DELAI_MIN_DEMANDE;
    
    @Value("${sygec.conge.solde.min:0.5}")
    private float SOLDE_MIN_REQUIS;
    
    @Autowired
    private DemandeCongeRepository demandeCongeRepository;
    
    @Autowired
    private PosteRepository posteRepository;
    
    @Autowired
    private BeneficiaireRepository beneficiaireRepository;
    
    @Autowired
    private ServiceeRepository serviceeRepository;
    
    @Autowired
    private TypeCongeRepository typeCongeRepository;
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private ApiSms apiSms;
    
    @Autowired
    private EmailServiceImpl emailServiceImpl;
    
    @Autowired
    private SoldeCongeRepository soldeCongeRepository;

    @Override
    @Transactional
    public DemandeCongeDto save(@NotNull DemandeCongeDto demandeCongeDto) {
    	  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
  	    Admin utilisateur = adminRepository.findByEmail(auth.getName())
  	            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        // Valider les données de la demande
        validateDemandeData(demandeCongeDto);
        
        // Vérifier les dates
        validateDates(demandeCongeDto.getDateDebut(), demandeCongeDto.getDateFin());
        
        // Calculer la durée du congé en jours ouvrables
        int joursConge = calculerJoursOuvrables(demandeCongeDto.getDateDebut(), demandeCongeDto.getDateFin());
        
        // Vérifier la durée minimale et maximale
        if (joursConge < DUREE_MIN_CONGE) {
            throw new CongeException("La durée du congé est inférieure à la durée minimale autorisée (" + DUREE_MIN_CONGE + " jour(s))");
        }
        
        if (joursConge > DUREE_MAX_CONGE) {
            throw new CongeException("La durée du congé est supérieure à la durée maximale autorisée (" + DUREE_MAX_CONGE + " jours)");
        }
        
        // Vérifier le délai minimum avant la date de début
        LocalDate dateDebut = LocalDate.parse(demandeCongeDto.getDateDebut(), formatter);
        LocalDate today = LocalDate.now();
        long joursAvantConge = ChronoUnit.DAYS.between(today, dateDebut);
        
        if (joursAvantConge < DELAI_MIN_DEMANDE) {
            throw new CongeException("La demande doit être faite au moins " + DELAI_MIN_DEMANDE + 
                " jours avant la date de début du congé");
        }
        
        // Vérifier si l'utilisateur a déjà une demande en attente
        List<DemandeConge> demandesEnAttente = demandeCongeRepository.findByBeneficiaireAndStatut(
                utilisateur.getBeneficiaire(), StatutDemande.EN_ATTENTE);
        
        if (!demandesEnAttente.isEmpty()) {
            throw new CongeException("Vous avez déjà une demande de congé en attente de validation");
        }
        
        // Vérifier le solde de congé disponible
        verifierSoldeCongeDisponible(utilisateur.getBeneficiaire(), joursConge, dateDebut.getYear());
        
        // Récupérer les entités associées
        Poste poste = posteRepository.findById(demandeCongeDto.getPoste_uuid())
                .orElseThrow(() -> new ResourceNotFoundException("Poste non trouvé"));
        Beneficiaire beneficiaire = beneficiaireRepository.findById(demandeCongeDto.getBeneficiaire_uuid())
                .orElseThrow(() -> new ResourceNotFoundException("Bénéficiaire non trouvé"));
        Servicee servicee = serviceeRepository.findById(demandeCongeDto.getServicee_uuid())
                .orElseThrow(() -> new ResourceNotFoundException("Service non trouvé"));
        TypeConge typeConge = typeCongeRepository.findById(demandeCongeDto.getTypeConge_uuid())
                .orElseThrow(() -> new ResourceNotFoundException("Type de congé non trouvé"));
        
        // Créer et initialiser la demande
        DemandeConge demandeConge = new DemandeConge();
        demandeConge.setBeneficiaire(utilisateur.getBeneficiaire());
        demandeConge.setCode(genererCodeDemande(beneficiaire));
        demandeConge.setDateDebut(demandeCongeDto.getDateDebut());
        demandeConge.setDateFin(demandeCongeDto.getDateFin());
        demandeConge.setMotif(demandeCongeDto.getMotif());
        demandeConge.setPoste(poste);
        demandeConge.setServicee(servicee);
        demandeConge.setTypeConge(typeConge);
        demandeConge.setStatut(StatutDemande.EN_ATTENTE);
        demandeConge.setValider(false);
        demandeConge.setRejetter(false);
        demandeConge.setAnnuler(false);
        demandeConge.setNombreJours(joursConge);
        
        DemandeConge demandeCongeSave = demandeCongeRepository.save(demandeConge);
        notifyChefService(demandeCongeSave);
        
        return Mapper.toClasseDto(demandeCongeSave);
    }
    
    private void validateDemandeData(DemandeCongeDto demandeCongeDto) {
        if (demandeCongeDto == null) {
            throw new IllegalArgumentException("Les données de la demande sont requises");
        }
        
        if (demandeCongeDto.getDateDebut() == null || demandeCongeDto.getDateDebut().trim().isEmpty()) {
            throw new IllegalArgumentException("La date de début est requise");
        }
        
        if (demandeCongeDto.getDateFin() == null || demandeCongeDto.getDateFin().trim().isEmpty()) {
            throw new IllegalArgumentException("La date de fin est requise");
        }
        
        if (demandeCongeDto.getMotif() == null || demandeCongeDto.getMotif().trim().isEmpty()) {
            throw new IllegalArgumentException("Le motif du congé est requis");
        }
        
        if (demandeCongeDto.getPoste_uuid() == null || demandeCongeDto.getPoste_uuid().trim().isEmpty()) {
            throw new IllegalArgumentException("Le poste est requis");
        }
        
        if (demandeCongeDto.getBeneficiaire_uuid() == null || demandeCongeDto.getBeneficiaire_uuid().trim().isEmpty()) {
            throw new IllegalArgumentException("Le bénéficiaire est requis");
        }
        
        if (demandeCongeDto.getServicee_uuid() == null || demandeCongeDto.getServicee_uuid().trim().isEmpty()) {
            throw new IllegalArgumentException("Le service est requis");
        }
        
        if (demandeCongeDto.getTypeConge_uuid() == null || demandeCongeDto.getTypeConge_uuid().trim().isEmpty()) {
            throw new IllegalArgumentException("Le type de congé est requis");
        }
    }
    
    private void verifierSoldeCongeDisponible(Beneficiaire beneficiaire, int joursConge, int annee) {
        SoldeConge soldeConge = soldeCongeRepository.findByBeneficiaireAndAnnee(beneficiaire, annee)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Aucun solde de congé trouvé pour le bénéficiaire pour l'année " + annee));
        
        if (soldeConge.getSoldeRestandt() < joursConge) {
            throw new CongeException(
                    "Solde de congé insuffisant. Solde actuel: " + soldeConge.getSoldeRestandt() + 
                    ", Jours demandés: " + joursConge);
        }
        
        if (soldeConge.getSoldeRestandt() < SOLDE_MIN_REQUIS) {
            throw new CongeException("Vous n'avez pas assez de jours de congé disponibles");
        }
    }
    
    // Génère un code unique pour la demande
    private String genererCodeDemande(Beneficiaire beneficiaire) {
        LocalDate now = LocalDate.now();
        String year = String.valueOf(now.getYear());
        String month = String.format("%02d", now.getMonthValue());
        String day = String.format("%02d", now.getDayOfMonth());
        
        // Récupérer le nombre de demandes pour ce bénéficiaire
        long count = demandeCongeRepository.countByBeneficiaire(beneficiaire);
        
        // Format: CONGE-INITIALES-AAAAMMJJ-COUNT
        String initiales = getInitiales(beneficiaire.getNom(), beneficiaire.getPrenom());
        return "CONGE-" + initiales + "-" + year + month + day + "-" + (count + 1);
    }
    
    private String getInitiales(String nom, String prenom) {
        StringBuilder initiales = new StringBuilder();
        if (nom != null && !nom.isEmpty()) {
            initiales.append(nom.charAt(0));
        }
        if (prenom != null && !prenom.isEmpty()) {
            initiales.append(prenom.charAt(0));
        }
        return initiales.toString().toUpperCase();
    }
    
    private int calculerJoursOuvrables(String dateDebutStr, String dateFinStr) {
        LocalDate dateDebut = LocalDate.parse(dateDebutStr, formatter);
        LocalDate dateFin = LocalDate.parse(dateFinStr, formatter);
        
        int joursOuvrables = 0;
        LocalDate date = dateDebut;
        
        while (!date.isAfter(dateFin)) {
            // Ne pas compter les weekends (samedi et dimanche)
            if (!(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                joursOuvrables++;
            }
            date = date.plusDays(1);
        }
        
        return joursOuvrables;
    }

    private void notifyChefService(DemandeConge demande) {
        if (demande.getServicee() != null && demande.getServicee().getUuid() != null) {
            beneficiaireRepository.findAll().stream()
                .filter(b -> b != null && 
                       b.getServicee() != null && 
                       b.getServicee().getUuid().equals(demande.getServicee().getUuid()) && 
                       b.getUser() != null && 
                       b.getUser().getRoles().contains(EnumRole.CHEF_SERVICE))
                .forEach(this::sendNotifications);
        }
    }
    
    private void sendNotifications(Beneficiaire beneficiaire) {
        if (beneficiaire.getPhone() != null) {
            sendMessageToChef(beneficiaire.getPhone(), "Chef de Service");
        }
        if (beneficiaire.getEmail() != null) {
            try {
                emailServiceImpl.sendHtmlBeneficiaireToChef(beneficiaire.getEmail());
            } catch (MessagingException e) {
                logger.error("Erreur d'envoi d'email", e);
            }
        }
    }
    
    public void sendMessageToChef(String tel, String fonction) {
        try {
            OrangeTokenDto orangeTokenDto = apiSms.generateToken();
            if (orangeTokenDto != null && orangeTokenDto.getAccessToken() != null) {
                String telephone = tel == null || tel.isEmpty() ? "" : "+224" + tel;
                String sms = "Salut " + fonction + " ,ce message provient de l'application SYGEC. Vous avez une demande en attente, veuillez vous connectez enfin d'approuver. Merci et bonne journée de travail";
                apiSms.sendSms(telephone, sms, orangeTokenDto.getAccessToken());
            } else {
                logger.error("Impossible d'obtenir le token SMS");
            }
        } catch (Exception e) {
            logger.error("Erreur lors de l'envoi du SMS au chef de service: {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public DemandeCongeDto update(@NotNull DemandeCongeDto demandeCongeDto, @NotBlank String uuid) {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("L'identifiant de la demande est requis");
        }
        
        DemandeConge demandeConge = demandeCongeRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Demande de congé non trouvée"));
        
        // Vérifier si la demande est déjà traitée
        if (demandeConge.getStatut() != StatutDemande.EN_ATTENTE && 
                !SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new CongeException("Impossible de modifier une demande déjà " + demandeConge.getStatut().toString().toLowerCase());
        }
        
        // Récupérer les entités associées
        Poste poste = posteRepository.findById(demandeCongeDto.getPoste_uuid())
                .orElseThrow(() -> new ResourceNotFoundException("Poste non trouvé"));
        Beneficiaire beneficiaire = beneficiaireRepository.findById(demandeCongeDto.getBeneficiaire_uuid())
                .orElseThrow(() -> new ResourceNotFoundException("Bénéficiaire non trouvé"));
        Servicee servicee = serviceeRepository.findById(demandeCongeDto.getServicee_uuid())
                .orElseThrow(() -> new ResourceNotFoundException("Service non trouvé"));
        TypeConge typeConge = typeCongeRepository.findById(demandeCongeDto.getTypeConge_uuid())
                .orElseThrow(() -> new ResourceNotFoundException("Type de congé non trouvé"));
        
        // Conserver l'état précédent pour les comparaisons
        StatutDemande statutPrecedent = demandeConge.getStatut();
        boolean etaitValide = demandeConge.isValider();
        
        // Mettre à jour les données de base
        demandeConge.setDateDebut(demandeCongeDto.getDateDebut());
        demandeConge.setDateFin(demandeCongeDto.getDateFin());
        demandeConge.setMotif(demandeCongeDto.getMotif());
        demandeConge.setPoste(poste);
        demandeConge.setBeneficiaire(beneficiaire);
        demandeConge.setServicee(servicee);
        demandeConge.setTypeConge(typeConge);
        
        // Mettre à jour le statut
        demandeConge.setValider(demandeCongeDto.isValider());
        demandeConge.setRejetter(demandeCongeDto.isRejetter());
        demandeConge.setAnnuler(demandeCongeDto.isAnnuler());
        
        // Mettre à jour le statut en fonction des flags
        if (demandeConge.isValider() && !demandeConge.isRejetter() && !demandeConge.isAnnuler()) {
            demandeConge.setStatut(StatutDemande.VALIDER);
            // Mettre à jour le solde de congé si la demande est nouvellement validée
            if (!etaitValide) {
                mettreAJourSoldeConge(demandeConge);
            }
        } else if (!demandeConge.isValider() && demandeConge.isRejetter() && !demandeConge.isAnnuler()) {
            demandeConge.setStatut(StatutDemande.REJETTER);
        } else if (!demandeConge.isValider() && !demandeConge.isRejetter() && demandeConge.isAnnuler()) {
            demandeConge.setStatut(StatutDemande.ANNULER);
            // Restaurer le solde de congé si la demande était validée et est maintenant annulée
            if (etaitValide) {
                restaurerSoldeConge(demandeConge);
            }
        } else {
            demandeConge.setStatut(StatutDemande.EN_ATTENTE);
        }
        
        // Gestion des notifications si le statut a changé
        if (statutPrecedent != demandeConge.getStatut()) {
            handleNotifications(demandeConge);
        }
        
        DemandeConge demandeCongeSave = demandeCongeRepository.save(demandeConge);
        return Mapper.toClasseDto(demandeCongeSave);
    }
    
    private void handleNotifications(DemandeConge demandeConge) {
       if (demandeConge.getStatut() == StatutDemande.VALIDER) {
           notifyBeneficiaire(demandeConge);
       } else if (demandeConge.getStatut() == StatutDemande.REJETTER) {
           notifyBeneficiaireRejetter(demandeConge);
       } else if (demandeConge.getStatut() == StatutDemande.ANNULER) {
           notifyBeneficiaireAnnuler(demandeConge);
       }
    }
    
    private void notifyBeneficiaire(DemandeConge demandeConge) {
       Beneficiaire beneficiaire = demandeConge.getBeneficiaire();
       if (beneficiaire != null) {
           sendMessageToBeneficiaire(beneficiaire.getPhone());
           try {
               emailServiceImpl.sendHtmlDGtoBenficiaire(beneficiaire.getEmail());
           } catch (MessagingException e) {
               logger.error("Erreur d'envoi d'email", e);
           }
       }
    }
    
    private void notifyBeneficiaireRejetter(DemandeConge demandeConge) {
       Beneficiaire beneficiaire = demandeConge.getBeneficiaire();
       if (beneficiaire != null) {
           sendMessageToBeneficiaireRejetter(beneficiaire.getPhone());
           try {
               emailServiceImpl.sendHtmlDGtoBenficiaireRejetter(beneficiaire.getEmail());
           } catch (MessagingException e) {
               logger.error("Erreur d'envoi d'email", e);
           }
       }
    }
    
    private void notifyBeneficiaireAnnuler(DemandeConge demandeConge) {
       Beneficiaire beneficiaire = demandeConge.getBeneficiaire();
       if (beneficiaire != null) {
           sendMessageToBeneficiaireAnnuler(beneficiaire.getPhone());
           try {
               emailServiceImpl.sendHtmlDGtoBenficiaireAnnuler(beneficiaire.getEmail());
           } catch (MessagingException e) {
               logger.error("Erreur d'envoi d'email", e);
           }
       }
    }
    
    public void sendMessageToBeneficiaire(String tel) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Admin admin = adminRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Admin non trouvé"));

            if (admin.getRoles() == null || admin.getRoles().isEmpty()) {
                throw new RuntimeException("Admin n'a pas de rôle");
            }

            String role = admin.getRoles().get(0).toString();
            OrangeTokenDto orangeTokenDto = apiSms.generateToken();
            
            if (orangeTokenDto == null || orangeTokenDto.getAccessToken() == null) {
                throw new RuntimeException("Échec génération token SMS");
            }

            String telephone = tel == null || tel.isEmpty() ? "" : "+224" + tel;
            String sms = String.format("Salut Mr/Mme, ce message provient de SYGEC. Votre demande de conge a été approuvée par %s, en attente de livraison.", role);

            apiSms.sendSms(telephone, sms, orangeTokenDto.getAccessToken());
        } catch (Exception e) {
            logger.error("Erreur envoi SMS au bénéficiaire: {}", e.getMessage());
        }
    }
    
    public void sendMessageToBeneficiaireRejetter(String tel) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Admin admin = adminRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Admin non trouvé"));

            if (admin.getRoles() == null || admin.getRoles().isEmpty()) {
                throw new RuntimeException("Admin n'a pas de rôle");
            }

            String role = admin.getRoles().get(0).toString();
            OrangeTokenDto orangeTokenDto = apiSms.generateToken();
            
            if (orangeTokenDto == null || orangeTokenDto.getAccessToken() == null) {
                throw new RuntimeException("Échec génération token SMS");
            }

            String telephone = tel == null || tel.isEmpty() ? "" : "+224" + tel;
            String sms = String.format("Salut Mr/Mme, ce message provient de SYGEC. Votre demande de conge a été Rejetter par %s, pour plus d'information veuillez contacter DRH.", role);

            apiSms.sendSms(telephone, sms, orangeTokenDto.getAccessToken());
        } catch (Exception e) {
            logger.error("Erreur envoi SMS au bénéficiaire: {}", e.getMessage());
        }
    }
    
    public void sendMessageToBeneficiaireAnnuler(String tel) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Admin admin = adminRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Admin non trouvé"));

            if (admin.getRoles() == null || admin.getRoles().isEmpty()) {
                throw new RuntimeException("Admin n'a pas de rôle");
            }

            String role = admin.getRoles().get(0).toString();
            OrangeTokenDto orangeTokenDto = apiSms.generateToken();
            
            if (orangeTokenDto == null || orangeTokenDto.getAccessToken() == null) {
                throw new RuntimeException("Échec génération token SMS");
            }

            String telephone = tel == null || tel.isEmpty() ? "" : "+224" + tel;
            String sms = String.format("Salut Mr/Mme, ce message provient de SYGEC. Votre demande de conge a été Annuler par %s, pour plus d'information veuillez contacter DRH", role);

            apiSms.sendSms(telephone, sms, orangeTokenDto.getAccessToken());
        } catch (Exception e) {
            logger.error("Erreur envoi SMS au bénéficiaire: {}", e.getMessage());
        }
    }

    @Override
    public DemandeCongeDto findById(@NotBlank String uuid) {
        if (uuid == null || uuid.trim().isEmpty()) {
            logger.info("l'ID est null ou vide");
            throw new IllegalArgumentException("L'identifiant est requis");
        }
        DemandeConge demandeConge = demandeCongeRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Demande de congé non trouvée"));
        return Mapper.toClasseDto(demandeConge);
    }

    @Override
    public List<DemandeCongeDto> findAll() {
        List<DemandeConge> demandeConges = demandeCongeRepository.findAll();
        return demandeConges.stream()
                .map(Mapper::toClasseDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<DemandeCongeDto> findAllIsValider() {
        List<DemandeConge> demandeConges = demandeCongeRepository.findAllIsValider();
        return demandeConges.stream()
                .map(Mapper::toClasseDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<DemandeCongeDto> findAllIsNotValider() {
        List<DemandeConge> demandeConges = demandeCongeRepository.findAllIsNotValider();
        return demandeConges.stream()
                .map(Mapper::toClasseDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<DemandeCongeDto> findAllIsRejetter() {
        List<DemandeConge> demandeConges = demandeCongeRepository.findAllIsRejetter();
        return demandeConges.stream()
                .map(Mapper::toClasseDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<DemandeCongeDto> findAllIsAnnuler() {
        List<DemandeConge> demandeConges = demandeCongeRepository.findAllIsAnnuler();
        return demandeConges.stream()
                .map(Mapper::toClasseDto)
                .collect(Collectors.toList());
    }

	@Override
	public void delete(String uuid) {
	if (uuid == null) {
		logger.info("l'ID n'existe pas");
		return;
	}
		demandeCongeRepository.deleteById(uuid);
	}
	
	 @Override
	    public List<DemandeCongeDto> findAllByBeneficiaire(String uuidBeneficiaire) {
	        if (uuidBeneficiaire == null || uuidBeneficiaire.trim().isEmpty()) {
	            throw new IllegalArgumentException("L'UUID du bénéficiaire est requis");
	        }
	        
	        Beneficiaire beneficiaire = beneficiaireRepository.findById(uuidBeneficiaire).orElseThrow(() -> new NoSuchElementException("Bénéficiaire non trouvé avec l'id: " + uuidBeneficiaire));
	        
	        List<DemandeConge> demandes = demandeCongeRepository.findByBeneficiaire(beneficiaire);
	        return demandes.stream().map(Mapper::toClasseDto).collect(Collectors.toList());
	    }
	 
	 /**
	     * Calcule le nombre de jours entre deux dates et met à jour le solde de congé
	     */
	 /**
	  * Calcule le nombre de jours ouvrables entre deux dates et met à jour le solde de congé
	  */
	 private void mettreAJourSoldeConge(DemandeConge demandeConge) {
	     Beneficiaire beneficiaire = demandeConge.getBeneficiaire();
	     if (beneficiaire == null) {
	         throw new IllegalStateException("Le bénéficiaire n'est pas défini pour cette demande de congé");
	     }

	     // Récupérer l'année en cours depuis la date de début
	     LocalDate dateDebut = LocalDate.parse(demandeConge.getDateDebut(), formatter);
	     int annee = dateDebut.getYear();

	     // Récupérer le solde de congé du bénéficiaire pour l'année concernée
	     SoldeConge soldeConge = soldeCongeRepository.findByBeneficiaireAndAnnee(beneficiaire, annee)
	             .orElseThrow(() -> new NoSuchElementException(
	                     "Aucun solde de congé trouvé pour le bénéficiaire " + beneficiaire.getUuid() + " pour l'année " + annee));

	     // Utiliser le nombre de jours ouvrables qui est stocké dans l'objet DemandeConge
	     int nombreJoursOuvrables = demandeConge.getNombreJours();
	     
	     // Si le nombre de jours n'est pas défini, le calculer
	     if (nombreJoursOuvrables <= 0) {
	         nombreJoursOuvrables = calculerJoursOuvrables(demandeConge.getDateDebut(), demandeConge.getDateFin());
	         // Mettre à jour le nombre de jours dans la demande
	         demandeConge.setNombreJours(nombreJoursOuvrables);
	     }
	     
	     // Vérifier si le solde restant est suffisant
	     if (soldeConge.getSoldeRestandt() < nombreJoursOuvrables) {
	         throw new IllegalStateException(
	                 "Solde de congé insuffisant. Solde actuel: " + soldeConge.getSoldeRestandt() + 
	                 ", Jours ouvrables demandés: " + nombreJoursOuvrables);
	     }

	     // Mettre à jour le solde restant
	     float nouveauSolde = soldeConge.getSoldeRestandt() - nombreJoursOuvrables;
	     soldeConge.setSoldeRestandt(nouveauSolde);
	     
	     // Sauvegarder les modifications
	     soldeCongeRepository.save(soldeConge);
	     
	     logger.info("Solde de congé mis à jour pour le bénéficiaire {}. Nouveau solde: {}",
	             beneficiaire.getUuid(), nouveauSolde);
	 }
	    
	 /**
	  * Restaure le solde de congé en cas d'annulation d'une demande validée
	  */
	 private void restaurerSoldeConge(DemandeConge demandeConge) {
	     Beneficiaire beneficiaire = demandeConge.getBeneficiaire();
	     if (beneficiaire == null) {
	         throw new IllegalStateException("Le bénéficiaire n'est pas défini pour cette demande de congé");
	     }

	     // Récupérer l'année depuis la date de début
	     LocalDate dateDebut = LocalDate.parse(demandeConge.getDateDebut(), formatter);
	     int annee = dateDebut.getYear();

	     // Récupérer le solde de congé du bénéficiaire pour l'année concernée
	     SoldeConge soldeConge = soldeCongeRepository.findByBeneficiaireAndAnnee(beneficiaire, annee)
	             .orElseThrow(() -> new NoSuchElementException(
	                     "Aucun solde de congé trouvé pour le bénéficiaire " + beneficiaire.getUuid() + " pour l'année " + annee));

	     // Utiliser le nombre de jours ouvrables stocké dans l'objet DemandeConge
	     int nombreJoursOuvrables = demandeConge.getNombreJours();
	     
	     // Si le nombre de jours n'est pas défini, le calculer
	     if (nombreJoursOuvrables <= 0) {
	         nombreJoursOuvrables = calculerJoursOuvrables(demandeConge.getDateDebut(), demandeConge.getDateFin());
	     }

	     // Mettre à jour le solde restant
	     float nouveauSolde = soldeConge.getSoldeRestandt() + nombreJoursOuvrables;
	     
	     // Vérifier que le nouveau solde ne dépasse pas le solde annuel
	     if (nouveauSolde > soldeConge.getSoldeAnnuel()) {
	         nouveauSolde = soldeConge.getSoldeAnnuel();
	     }
	     
	     soldeConge.setSoldeRestandt(nouveauSolde);
	     
	     // Sauvegarder les modifications
	     soldeCongeRepository.save(soldeConge);
	     
	     logger.info("Solde de congé restauré pour le bénéficiaire {}. Nouveau solde: {}",
	             beneficiaire.getUuid(), nouveauSolde);
	 }
	    
	    
	    /**
	     * Valide les dates de début et de fin
	     */
	    private void validateDates(String dateDebut, String dateFin) {
	        if (dateDebut == null || dateDebut.trim().isEmpty()) {
	            throw new IllegalArgumentException("La date de début est requise");
	        }
	        
	        if (dateFin == null || dateFin.trim().isEmpty()) {
	            throw new IllegalArgumentException("La date de fin est requise");
	        }
	        
	        LocalDate debut = LocalDate.parse(dateDebut, formatter);
	        LocalDate fin = LocalDate.parse(dateFin, formatter);
	        
	        if (fin.isBefore(debut)) {
	            throw new IllegalArgumentException("La date de fin ne peut pas être antérieure à la date de début");
	        }
	        
	        // Vérifier si la période demandée ne chevauche pas d'autres congés validés
	        List<DemandeConge> demandesEnCours = demandeCongeRepository.findOverlappingValidatedRequests(dateDebut, dateFin);
	        if (!demandesEnCours.isEmpty()) {
	            throw new IllegalStateException("La période demandée chevauche des congés déjà validés");
	        }
	    }
	    
	

}
