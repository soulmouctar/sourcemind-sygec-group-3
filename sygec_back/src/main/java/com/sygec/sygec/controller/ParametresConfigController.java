package com.sygec.sygec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.sygec.sygec.dto.ParametresConfigDto;
import com.sygec.sygec.service.ParametresConfigService;

@RestController
@RequestMapping("/api/parametres-config")
@CrossOrigin
public class ParametresConfigController {

    @Autowired
    private ParametresConfigService parametresConfigService;

    @GetMapping
    public ResponseEntity<ParametresConfigDto> getParametresConfig() {
        ParametresConfigDto parametres = parametresConfigService.getParametres();
        return ResponseEntity.ok(parametres);
    }

    @PutMapping
    // Modifier ou supprimer l'annotation PreAuthorize selon les besoins
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParametresConfigDto> updateParametres(@RequestBody ParametresConfigDto parametresDto) {
        try {
            ParametresConfigDto updatedParametres = parametresConfigService.updateParametres(parametresDto);
            return ResponseEntity.ok(updatedParametres);
        } catch (Exception e) {
            // Log l'erreur pour le débogage
            System.err.println("Erreur lors de la mise à jour des paramètres: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    @PostMapping("/reset")
    public ResponseEntity<ParametresConfigDto> resetParametres() {
        try {
            ParametresConfigDto defaultParams = parametresConfigService.resetParametres();
            return ResponseEntity.ok(defaultParams);
        } catch (Exception e) {
            System.err.println("Erreur lors de la réinitialisation des paramètres: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}