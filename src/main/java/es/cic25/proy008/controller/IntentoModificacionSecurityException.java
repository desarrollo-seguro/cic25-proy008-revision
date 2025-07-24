package es.cic25.proy008.controller;

public class IntentoModificacionSecurityException extends RuntimeException {

    public IntentoModificacionSecurityException() {

    }
    
    public IntentoModificacionSecurityException(String message) {
        super(message);
    }

    public IntentoModificacionSecurityException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
