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

import es.cic25.proy008.exception.PatrocinadoresException;
import es.cic25.proy008.model.Patrocinadores;
import es.cic25.proy008.service.PatrocinadoresService;

@RestController
@RequestMapping("/patrocinadores")
public class PatrocinadoresController {

    @Autowired
    private PatrocinadoresService patrocinadoresService;

    @PostMapping
    public long create(@RequestBody Patrocinadores patrocinadores) {
        return patrocinadoresService.create(patrocinadores);
    }

    @GetMapping("/{id}")
    public Patrocinadores get(@PathVariable long id) {
        Patrocinadores escuderia = patrocinadoresService.get(id);
        if (escuderia == null) {
            throw new PatrocinadoresException(id);
        }
        return escuderia;
    }

    @GetMapping
    public List<Patrocinadores> get() {
        return patrocinadoresService.get();
    }

    @PutMapping
    public void update(@RequestBody Patrocinadores patrocinadores) {
        patrocinadoresService.update(patrocinadores);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        patrocinadoresService.delete(id);
    }

}
