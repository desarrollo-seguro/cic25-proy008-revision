package es.cic25.proy008.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.cic25.proy008.model.Escuderia;
import es.cic25.proy008.repository.EscuderiaRepository;

@Service
public class EscuderiaService {

     @Autowired
    private EscuderiaRepository escuderiaRepository;


    public long create(Escuderia escuderia) {
        
        escuderiaRepository.save(escuderia);

        return escuderia.getId();
    }

    public Escuderia get(long id) {
        Optional<Escuderia> resultado = escuderiaRepository.findById(id);
        return resultado.orElse(null);
    }

    public List<Escuderia> get() {
        return escuderiaRepository.findAll();
    }

    public void update(Escuderia escuderia) {
        escuderiaRepository.save(escuderia);
    }

    public void delete(long id) {
        escuderiaRepository.deleteById(id);
    }

}
