package es.cic25.proy008.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import es.cic25.proy008.controller.IdsNoCoincidenException;
import es.cic25.proy008.controller.IntentoCreacionSecurityException;
import es.cic25.proy008.controller.IntentoModificacionSecurityException;
// import es.cic25.proy008.controller.IntentoModificacionSecurityException;

@RestControllerAdvice
public class MiControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IntentoModificacionSecurityException.class)
    public void controloIntentoModificacion() {

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IntentoCreacionSecurityException.class)
    public void controloIntentoCreacion() {

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IdsNoCoincidenException.class)
    public void controloIdsNoCoinciden() {

    }

    // @ResponseStatus(HttpStatus.BAD_REQUEST)
    // @ExceptionHandler(IntentoEliminacionSecurityException.class)
    // public void controloIntentoEliminacion() {

    // }
}
