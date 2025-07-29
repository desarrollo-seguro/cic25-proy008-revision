package es.cic25.proy008.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.cic25.proy008.model.Equipo;
import es.cic25.proy008.model.Patrocinador;
import es.cic25.proy008.service.EquipoService;
import es.cic25.proy008.service.PatrocinadorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/equipo")
public class EquipoController {

    static final Logger LOGGER = LoggerFactory.getLogger(EquipoController.class);
    @Autowired
    private EquipoService equipoService;
    
    @Autowired
    private PatrocinadorService patrocinadorService;

    // C
    @PostMapping
    public Equipo create(@RequestBody Equipo equipo) {
        if (equipo.getId() != null) {
            LOGGER.info("La petición para crear el equipo " + equipo + " tenía un id distinto de null, luego no puede ser procesada");
            throw new IntentoModificacionSecurityException("Has intentado modificar el id: ilegal");
        }
        else {
            LOGGER.info("Se está gestionando la petición para crear el equipo" + equipo);
            equipo = equipoService.create(equipo);
            return equipo;
        }
    }

    // R (1)
    @GetMapping("/{id}")
    public Optional<Equipo> get(@PathVariable Long id) {
        LOGGER.info("Se está gestionando la petición para leer el equipo de id " + id);
        Optional<Equipo> equipo = equipoService.get(id);
        return equipo;
    }

    // R (All)
    @GetMapping
    public List<Equipo> get() {
        LOGGER.info("Se está gestionando la petición para listar todos los equipos");
        List<Equipo> listaEquipos = equipoService.get();
        return listaEquipos;
    }

    // U
    @PutMapping("/{id}")
    public Equipo update(@PathVariable(required = true) Long id, @RequestBody(required = true) Equipo equipo) {
        if (id.equals(equipo.getId())) { // es decir, si no hay incongruencias y las ids coinciden.
            if (equipoService.get(id).isEmpty()) { // si no hay ningún equipo en base de datos cuya id sea la que se pasa
                LOGGER.error("Se ha intentado actualizar un equipo sin estar en base de datos");
                throw new IntentoCreacionSecurityException("Has intentado modificar un equipo, " + equipo + " que no está en base de datos");
            }
            else {
                LOGGER.info("Se está gestionando la petición para leer el equipo " + equipo);
                equipo = equipoService.update(equipo);
                return equipo;
            }
        }
        else { // es decir, si las ids no coinciden
            LOGGER.error("Se ha intentado actualizar un equipo dando ids contradictorias en cuerpo y URL");
            throw new IdsNoCoincidenException("La id del cuerpo de la petición y la de la URL no coinciden");
        }
    }

    // D
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        LOGGER.info("Se está gestionando la petición para eliminar el equipo de id " + id);
        equipoService.delete(id);

        // Optional<Equipo> equipo = equipoService.get(id);
        // if (equipo == null) {
            // LOGGER.info("La petición para borrar un id que no existe en base de datos no puede ser gestionada");
            // throw new IntentoEliminacionSecurityException("Has intentado eliminar un equipo cuya id, " + id + " no está en base de datos");
        // }
        // else {
            // LOGGER.info("Se está gestionando la petición para eliminar el equipo de id " + id);
            // equipoService.delete(id);
        // }
    }

    // C (patrocinador en equipo)
    @PostMapping("/patrocinio")
    public Patrocinador create(@RequestBody Patrocinador patrocinador){
        LOGGER.info("Se está gestionando la petición para crear un patrocinador y consigo el equipo que trae");
        Patrocinador patrocinadorCreado = patrocinadorService.create(patrocinador);

        return patrocinadorCreado;
    }
}
