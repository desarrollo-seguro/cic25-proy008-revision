package es.cic25.proy008.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic25.proy008.service.PatrocinadorService;
import es.cic25.proy008.model.Patrocinador;
import es.cic25.proy008.repository.PatrocinadorRepository;

@Service
@Transactional
public class PatrocinadorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatrocinadorService.class);
    
    @Autowired
    private PatrocinadorRepository patrocinadorRepository;

    // C
    public Patrocinador create(Patrocinador patrocinador) {
        LOGGER.info("se está creando el patrocinador " + patrocinador);
        patrocinador = patrocinadorRepository.save(patrocinador);
        return patrocinador;
    }

    // R (1)
    @Transactional(readOnly = true)
    public Optional<Patrocinador> get(Long id) {
        LOGGER.info("se está buscando el patrocinador (si existe) de id " + id);
        Optional<Patrocinador> patrocinador = patrocinadorRepository.findById(id);
        return patrocinador;
    }

    // R (All)
    @Transactional(readOnly = true)
    public List<Patrocinador> get() {
        LOGGER.info("se está tratando de listar todos los patrocinadores");
        List<Patrocinador> patrocinadores = patrocinadorRepository.findAll();
        return patrocinadores;
    }

    // U
    public Patrocinador update(Patrocinador patrocinador) {
        LOGGER.info("se está actualizando el patrocinador " + patrocinador);
        patrocinador = patrocinadorRepository.save(patrocinador);
        return patrocinador;
    }

    // D
    public void delete(Long id) {
        LOGGER.info("se está eliminando el patrocinador de id " + id);
        patrocinadorRepository.deleteById(id);
    }
}

