package es.cic25.proy008.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MotorException extends RuntimeException {

    public MotorException(long id) {
        super("Motor con id " + id + " no encontrado.");
    }
}
