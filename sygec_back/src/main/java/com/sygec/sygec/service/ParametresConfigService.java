package com.sygec.sygec.service;

import com.sygec.sygec.dto.ParametresConfigDto;

public interface ParametresConfigService {
    /**
     * Récupère les paramètres de configuration actuels
     */
    ParametresConfigDto getParametres();
    
    /**
     * Met à jour les paramètres de configuration
     */
    ParametresConfigDto updateParametres(ParametresConfigDto parametresDto);
    
    /**
     * Réinitialise les paramètres aux valeurs par défaut
     */
    ParametresConfigDto resetParametres();
}