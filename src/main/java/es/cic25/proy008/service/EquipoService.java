package es.cic25.proy008.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.cic25.proy008.service.EquipoService;
import es.cic25.proy008.model.Equipo;
import es.cic25.proy008.repository.EquipoRepository;

@Transactional
@Service
public class EquipoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EquipoService.class);
    
    @Autowired
    private EquipoRepository equipoRepository;

    // C
    public Equipo create(Equipo equipo) {
        LOGGER.info("se está creando el equipo " + equipo);
        equipo = equipoRepository.save(equipo);
        return equipo;
    }

    // R (1)
    @Transactional(readOnly = true)
    public Optional<Equipo> get(Long id) {
        LOGGER.info("se está buscando el equipo (si existe) de id " + id);
        Optional<Equipo> equipo = equipoRepository.findById(id);
        return equipo;
    }

    // R (All)
    @Transactional(readOnly = true)
    public List<Equipo> get() {
        LOGGER.info("se está tratando de listar todos los equipos");
        List<Equipo> equipos = equipoRepository.findAll();
        return equipos;
    }

    // U
    public Equipo update(Equipo equipo) {
        LOGGER.info("se está actualizando el equipo " + equipo);
        equipo = equipoRepository.save(equipo);
        return equipo;
    }

    // D
    public void delete(Long id) {
        LOGGER.info("se está eliminando el equipo de id " + id);
        equipoRepository.deleteById(id);
    }
}
