package es.cic25.proy008.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.cic25.proy008.exception.EscuderiaException;
import es.cic25.proy008.model.Escuderia;
import es.cic25.proy008.service.EscuderiaService;

@RestController
@RequestMapping("/escuderias")
public class EscuderiaController {

    @Autowired
    private EscuderiaService escuderiaService;

    @PostMapping
    public long create(@RequestBody Escuderia escuderia) {
        return escuderiaService.create(escuderia);
    }

    @GetMapping("/{id}")
    public Escuderia get(@PathVariable long id) {
        Escuderia escuderia = escuderiaService.get(id);
        if (escuderia == null) {
            throw new EscuderiaException(id);
        }
        return escuderia;
    }

    @GetMapping
    public List<Escuderia> get() {
        return escuderiaService.get();
    }

    @PutMapping
    public void update(@RequestBody Escuderia escuderia) {
        escuderiaService.update(escuderia);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        escuderiaService.delete(id);
    }
}

