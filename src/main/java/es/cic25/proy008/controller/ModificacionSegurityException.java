package es.cic25.proy008.controller;

public class ModificacionSegurityException extends RuntimeException {

    public ModificacionSegurityException() {
        super("Has tratado de modificar mediante creacion");
    }

    public ModificacionSegurityException(String message ) {
        super(message);
    }

    public ModificacionSegurityException(String message, Throwable cause) {
        super(message, cause);
    }

}
