package es.cic25.proy008.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PatrocinadoresException extends RuntimeException {

    public PatrocinadoresException(long id) {
        super("Patrocinador con id " + id + " no encontrado.");
    }

}
