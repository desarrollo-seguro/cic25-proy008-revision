package es.cic25.proy008.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.cic25.proy008.model.Patrocinadores;
import es.cic25.proy008.repository.PatrocinadoresRepository;

@Service
public class PatrocinadoresService {

    @Autowired
    private PatrocinadoresRepository patrocinadoresRepository;

    public long create(Patrocinadores patrocinadores) {
        patrocinadoresRepository.save(patrocinadores);
        return patrocinadores.getId();
    }

    public Patrocinadores get(long id) {
        Optional<Patrocinadores> resultado = patrocinadoresRepository.findById(id);
        return resultado.orElse(null);
    }

    public List<Patrocinadores> get() {
        return patrocinadoresRepository.findAll();
    }

    public void update(Patrocinadores patrocinadores) {
        patrocinadoresRepository.save(patrocinadores);
    }

    public void delete(long id) {
        patrocinadoresRepository.deleteById(id);
    }
}
