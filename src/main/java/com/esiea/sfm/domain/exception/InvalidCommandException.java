package com.esiea.sfm.domain.exception;

// pour fichier qui manquent
public class InvalidCommandException extends RuntimeException {
    public InvalidCommandException(String message) { super(message); }
}