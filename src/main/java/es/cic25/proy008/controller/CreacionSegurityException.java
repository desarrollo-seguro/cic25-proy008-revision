package es.cic25.proy008.controller;

public class CreacionSegurityException extends RuntimeException {

    public CreacionSegurityException() {
        super("Intento de creación en el update");
    }

    public CreacionSegurityException(String mensaje) {
        super(mensaje);
    }

    public CreacionSegurityException(String mensaje, Throwable throwable) {
        super(mensaje, throwable);
    }
}
