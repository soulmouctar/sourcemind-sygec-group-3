package com.sygec.sygec.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception spécifique pour les erreurs liées aux opérations de congé.
 * Cette exception est utilisée pour signaler les problèmes métier
 * liés au traitement des demandes de congé.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CongeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Crée une nouvelle instance de CongeException sans message.
     */
    public CongeException() {
        super();
    }

    /**
     * Crée une nouvelle instance de CongeException avec le message spécifié.
     *
     * @param message Le message d'erreur détaillé
     */
    public CongeException(String message) {
        super(message);
    }

    /**
     * Crée une nouvelle instance de CongeException avec le message et la cause spécifiés.
     *
     * @param message Le message d'erreur détaillé
     * @param cause   La cause de l'exception
     */
    public CongeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Crée une nouvelle instance de CongeException avec la cause spécifiée.
     *
     * @param cause La cause de l'exception
     */
    public CongeException(Throwable cause) {
        super(cause);
    }
}