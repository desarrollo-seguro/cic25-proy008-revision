package es.cic25.proy008.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic25.proy008.model.Equipo;
import es.cic25.proy008.repository.EquipoRepository;
import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EquipoControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc; // voy a simular una petición HTTP
    @Autowired
    ObjectMapper objectMapper; // y me ayudaré de objectMapper para conversiones json-string y string-json
    @Autowired
    private EquipoRepository equipoRepository;

    // testeamos create() en caso de que el id que se le pase sea null, es decir, que no se le intente pasar un id inicialmente
    @Test
    void testCreate() throws Exception { // el "throws Exception" se hace por JsonProcessingException
        Equipo equipo = new Equipo();
        equipo.setNombre("Racing de Santander");
        equipo.setMejorJugador("Iñigo Vicente");
        equipo.setNumeroVictoria(0);
        equipo.setDescenso(false);

        String equipoJson = objectMapper.writeValueAsString(equipo); // simulo que este es el body de mi petición, de modo que
                                                                     // convierto a String el Equipo que he creado, es decir, lo convierto
                                                                     // a formato Json

        mockMvc.perform(post("/equipo")
                .contentType("application/json")
                .content(equipoJson)) // hasta aquí, he simulado una petición cuyo cuerpo es el string antes creado
                .andExpect(status().isOk()) // es decir, que dé un 200
                .andExpect(result -> {
                                String respuesta = result.getResponse().getContentAsString(); // convierto el Json de respuesta a String
                                Equipo registroCreado = objectMapper.readValue(respuesta, Equipo.class); // convierto el String respuesta a un objeto de Equipo
                                assertTrue(registroCreado.getId() != null, "El valor no debe ser null"); // y verifico que su id no sea null, es decir, que se haya creado

                                Optional<Equipo> registroRealmenteCreado = equipoRepository.findById(registroCreado.getId()); // verifico que hay un objeto con esta id en base de datos
                                assertTrue(registroRealmenteCreado.isPresent()); // y que está guardado

                });
    }

    @Test
    void testCreateModificacion() throws Exception { // el "throws Exception" se hace por JsonProcessingException
        Equipo equipo = new Equipo();
        equipo.setNombre("Racing de Santander");
        equipo.setMejorJugador("Iñigo Vicente");
        equipo.setNumeroVictoria(0);
        equipo.setDescenso(false);
        equipo.setId(1L); // voy a tratar de que salte la excepción y, en última instancia, dé un BAD_REQUEST

        String equipoJson = objectMapper.writeValueAsString(equipo); // simulo que este es el body de mi petición, de modo que
                                                                     // convierto a String el Equipo que he creado, es decir, lo convierto
                                                                     // a formato Json

        mockMvc.perform(post("/equipo")
                .contentType("application/json")
                .content(equipoJson)) // hasta aquí, he simulado una petición cuyo cuerpo es el string antes creado
                .andExpect(status().isBadRequest()); // y aquí verifico que habiendo asignado id manualmente se obtiene BAD_REQUEST
    }
    @Test
    void testDelete() throws Exception {
        Equipo equipo = new Equipo();
        equipo.setNombre("Racing de Santander");
        equipo.setMejorJugador("Iñigo Vicente");
        equipo.setNumeroVictoria(0);
        equipo.setDescenso(false);

        String equipoJson = objectMapper.writeValueAsString(equipo); 
        
        // primeramente, simulo un post para crear un Equipo
        mockMvc.perform(post("/equipo")
                .contentType("application/json")
                .content(equipoJson))
                .andExpect(status().isOk());
        
        // pruebo get(Long id) para que sea de integración
        mockMvc.perform(get("/equipo/1")) // habría problemas de concurrencia... de momento no los gestionamos
                .andExpect(status().isOk());
        
        // y ahora compruebo que lo he borrado adecuadamente
        mockMvc.perform(delete("/equipo/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetOne() throws Exception {
        Equipo equipo = new Equipo();
        equipo.setNombre("Racing de Santander");
        equipo.setMejorJugador("Iñigo Vicente");
        equipo.setNumeroVictoria(0);
        equipo.setDescenso(false);

        String equipoJson = objectMapper.writeValueAsString(equipo); 

        // primeramente, simulo un post para crear un Equipo
        mockMvc.perform(post("/equipo")
                .contentType("application/json")
                .content(equipoJson))
                .andExpect(status().isOk());
    
        mockMvc.perform(get("/equipo/1")) // habría problemas de concurrencia... de momento no los gestionamos
                .andExpect(status().isOk());
    }

    @Test
    void testGetAll() {

    }

    @Test
    void testUpdate() {

    }
}
