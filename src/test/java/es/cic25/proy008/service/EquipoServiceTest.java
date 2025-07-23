package es.cic25.proy008.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import es.cic25.proy008.model.Equipo;

@SpringBootTest
@Transactional
public class EquipoServiceTest {
    @Autowired
    private EquipoService equipoService;

    @Test
    void testCreate() {
        Equipo equipo = new Equipo();
        equipo.setNombre("Racing de Santander");
        equipo.setMejorJugador("Iñigo Vicente");
        equipo.setNumeroVictoria(0);
        equipo.setDescenso(false);
        equipoService.create(equipo);
        Long idEquipo = equipo.getId();
        assertTrue(idEquipo == 1);
    }

    @Test
    void testDelete() {
        Equipo equipo = new Equipo();
        equipo.setNombre("Racing de Santander");
        equipo.setMejorJugador("Iñigo Vicente");
        equipo.setNumeroVictoria(0);
        equipo.setDescenso(false);
        equipoService.create(equipo);
        Long idEquipo = equipo.getId();
        equipoService.delete(idEquipo);
        assertTrue(equipoService.get(idEquipo).isEmpty());

    }

    @Test
    void testGetAll() {
        Equipo equipo1 = new Equipo();
        equipo1.setNombre("Racing de Santander");
        equipo1.setMejorJugador("Iñigo Vicente");
        equipo1.setNumeroVictoria(0);
        equipo1.setDescenso(false);
        equipoService.create(equipo1);

        Equipo equipo2 = new Equipo();
        equipo2.setNombre("Sporting de Gijón");
        equipo2.setMejorJugador("Juan Otero");
        equipo2.setNumeroVictoria(2);
        equipo2.setDescenso(false);
        equipoService.create(equipo2);

        List<Equipo> listaEquipos = equipoService.get();

        assertEquals(listaEquipos.size(), 2);
    }

    @Test
    void testGetOne() {
        Equipo equipo = new Equipo();
        equipo.setNombre("Racing de Santander");
        equipo.setMejorJugador("Iñigo Vicente");
        equipo.setNumeroVictoria(0);
        equipo.setDescenso(false);
        equipoService.create(equipo);
        Long idEquipo = equipo.getId();
        Optional<Equipo> equipoRecibido = equipoService.get(idEquipo);
        Equipo equipoRecibidoSinOptional = equipoRecibido.get(); // porque es un Equipo, para poder compararlo
        assertEquals(equipoRecibidoSinOptional, equipo);
    }

    @Test
    void testUpdate() {
        Equipo equipo = new Equipo();
        equipo.setNombre("Racing de Santander");
        equipo.setMejorJugador("Iñigo Vicente");
        equipo.setNumeroVictoria(0);
        equipo.setDescenso(false);
        equipo = equipoService.create(equipo);
        equipo.setMejorJugador("Andrés Martín");
        equipo.setNumeroVictoria(1);
        equipoService.update(equipo);
        assertEquals(equipo.getMejorJugador(), "Andrés Martín");
        assertTrue(equipo.getNumeroVictoria() == 1);
    }
}
