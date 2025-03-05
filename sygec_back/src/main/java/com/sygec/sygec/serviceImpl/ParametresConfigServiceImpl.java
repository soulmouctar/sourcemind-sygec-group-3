package com.sygec.sygec.serviceImpl;

import com.sygec.sygec.dto.ParametresConfigDto;
import com.sygec.sygec.model.ParametresConfig;
import com.sygec.sygec.repository.ParametresConfigRepository;
import com.sygec.sygec.service.ParametresConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Date;

@Service
public class ParametresConfigServiceImpl implements ParametresConfigService {

    @Autowired
    private ParametresConfigRepository parametresConfigRepository;

    @Override
    public ParametresConfigDto getParametres() {
        Optional<ParametresConfig> parametresOpt = parametresConfigRepository.findActifParametres();
        if (parametresOpt.isPresent()) {
            return toDto(parametresOpt.get());
        } else {
            // Retourner des valeurs par défaut si aucun paramètre n'est trouvé
            ParametresConfigDto defaultParams = new ParametresConfigDto();
            defaultParams.setDureeMinConge(1);
            defaultParams.setDureeMaxConge(30);
            defaultParams.setDelaiMinDemande(2);
            defaultParams.setSoldeMinRequis(1);
            return defaultParams;
        }
    }

    @Override
    @Transactional
    public ParametresConfigDto updateParametres(ParametresConfigDto parametresDto) {
        try {
            // Validation des valeurs
            validateParametres(parametresDto);

            // Désactiver tous les paramètres existants
            List<ParametresConfig> allParams = parametresConfigRepository.findAll();
            for (ParametresConfig param : allParams) {
                param.setActif(false);
                parametresConfigRepository.save(param);
            }

            // Créer une nouvelle configuration au lieu de modifier l'existante
            ParametresConfig parametres = new ParametresConfig();
            
            // Définir explicitement tous les champs, y compris ceux hérités
            parametres.setUuid(UUID.randomUUID().toString());
            parametres.setSupprimer(false);
            parametres.setDateCreated(new Date());
            parametres.setLastUpdated(new Date());
            
            // Mettre à jour avec les nouvelles valeurs
            parametres.setDureeMinConge(parametresDto.getDureeMinConge());
            parametres.setDureeMaxConge(parametresDto.getDureeMaxConge());
            parametres.setDelaiMinDemande(parametresDto.getDelaiMinDemande());
            parametres.setSoldeMinRequis(parametresDto.getSoldeMinRequis());
            parametres.setActif(true);

            // Sauvegarder et retourner
            ParametresConfig savedParametres = parametresConfigRepository.save(parametres);
            return toDto(savedParametres);
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour des paramètres: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    private void validateParametres(ParametresConfigDto parametresDto) {
        if (parametresDto.getDureeMinConge() == null || parametresDto.getDureeMinConge() < 1) {
            throw new IllegalArgumentException("La durée minimale de congé doit être d'au moins 1 jour");
        }
        if (parametresDto.getDureeMaxConge() == null || parametresDto.getDureeMaxConge() < parametresDto.getDureeMinConge()) {
            throw new IllegalArgumentException("La durée maximale de congé doit être supérieure à la durée minimale");
        }
        if (parametresDto.getDelaiMinDemande() == null || parametresDto.getDelaiMinDemande() < 0) {
            throw new IllegalArgumentException("Le délai minimum de demande ne peut pas être négatif");
        }
        if (parametresDto.getSoldeMinRequis() == null || parametresDto.getSoldeMinRequis() < 0) {
            throw new IllegalArgumentException("Le solde minimum requis ne peut pas être négatif");
        }
    }

    private ParametresConfigDto toDto(ParametresConfig parametres) {
        ParametresConfigDto dto = new ParametresConfigDto();
        dto.setUuid(parametres.getUuid());
        dto.setDureeMinConge(parametres.getDureeMinConge());
        dto.setDureeMaxConge(parametres.getDureeMaxConge());
        dto.setDelaiMinDemande(parametres.getDelaiMinDemande());
        dto.setSoldeMinRequis(parametres.getSoldeMinRequis());
        return dto;
    }

	@Override
	public ParametresConfigDto resetParametres() {
		    // Désactiver tous les paramètres existants
		    List<ParametresConfig> allParams = parametresConfigRepository.findAll();
		    for (ParametresConfig param : allParams) {
		        param.setActif(false);
		        parametresConfigRepository.save(param);
		    }
		    
		    // Créer une nouvelle configuration avec les valeurs par défaut
		    ParametresConfig defaultParams = new ParametresConfig();
		    defaultParams.setUuid(UUID.randomUUID().toString());
		    defaultParams.setSupprimer(false);
		    defaultParams.setDateCreated(new Date());
		    defaultParams.setLastUpdated(new Date());
		    
		    // Définir les valeurs par défaut
		    defaultParams.setDureeMinConge(1);
		    defaultParams.setDureeMaxConge(30);
		    defaultParams.setDelaiMinDemande(2);
		    defaultParams.setSoldeMinRequis(1);
		    defaultParams.setActif(true);
		    
		    // Sauvegarder et retourner
		    ParametresConfig savedParams = parametresConfigRepository.save(defaultParams);
		    return toDto(savedParams);
		
	}
}