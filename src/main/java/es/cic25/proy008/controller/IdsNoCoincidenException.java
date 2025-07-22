package es.cic25.proy008.controller;

public class IdsNoCoincidenException extends RuntimeException {

    public IdsNoCoincidenException() {

    }
    
    public IdsNoCoincidenException(String message) {
        super(message);
    }

    public IdsNoCoincidenException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
