package es.cic25.proy008.controller;

public class IntentoCreacionSecurityException extends RuntimeException {

    public IntentoCreacionSecurityException() {

    }
    
    public IntentoCreacionSecurityException(String message) {
        super(message);
    }

    public IntentoCreacionSecurityException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
