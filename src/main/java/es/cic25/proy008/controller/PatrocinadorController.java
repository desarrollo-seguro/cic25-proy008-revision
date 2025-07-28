package es.cic25.proy008.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.cic25.proy008.model.Patrocinador;
import es.cic25.proy008.service.PatrocinadorService;

@RestController
@RequestMapping("/patrocinador")
public class PatrocinadorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatrocinadorController.class);

    @Autowired
    private PatrocinadorService patrocinadorService;
    
    // C
    @PostMapping
    public Patrocinador create(@RequestBody Patrocinador patrocinador) {
        if (patrocinador.getId() != null) {
            LOGGER.error("La petición para crear el equipo " + patrocinador + " tenía un id distinto de null, luego no puede ser procesada");
            throw new IntentoModificacionSecurityException("Has intentado modificar el id: ilegal");
        }
        else {
            LOGGER.info("Se está gestionando la petición para crear el patrocinador" + patrocinador);
            patrocinador = patrocinadorService.create(patrocinador);
            return patrocinador;
        }
    }

    // R (1)
    @GetMapping("/{id}")
    public Optional<Patrocinador> get(@PathVariable Long id) {
        LOGGER.info("Se está gestionando la petición para leer el patrocinador de id " + id);
        Optional<Patrocinador> patrocinador = patrocinadorService.get(id);
        return patrocinador;
    }

    // R (All)
    @GetMapping
    public List<Patrocinador> get() {
        LOGGER.info("Se está gestionando la petición para listar todos los patrocinadores");
        List<Patrocinador> listaPatrocinadores = patrocinadorService.get();
        return listaPatrocinadores;
    }

    // U
    @PutMapping("/{id}")
    public Patrocinador update(@PathVariable(required = true) Long id, @RequestBody(required = true) Patrocinador patrocinador) {
        if (id.equals(patrocinador.getId())) { // es decir, si no hay incongruencias y las ids coinciden.
            if (patrocinadorService.get(id).isEmpty()) { // si no hay ningún patrocinador en base de datos cuya id sea la que se pasa
                LOGGER.error("Se ha intentado actualizar un patrocinador sin estar en base de datos");
                throw new IntentoCreacionSecurityException("Has intentado modificar un patrocinador, " + patrocinador + " que no está en base de datos");
            }
            else {
                LOGGER.info("Se está gestionando la petición para leer el patrocinador " + patrocinador);
                patrocinador = patrocinadorService.update(patrocinador);
                return patrocinador;
            }
        }
        else { // es decir, si las ids no coinciden
            LOGGER.error("Se ha intentado actualizar un patrocinador dando ids contradictorias en cuerpo y URL");
            throw new IdsNoCoincidenException();
        }
    }

    // D
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        LOGGER.info("Se está gestionando la petición para eliminar el equipo de id " + id);
        patrocinadorService.delete(id);
    }
}