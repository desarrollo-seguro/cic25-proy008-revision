package es.cic25.proy008.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
                .andDo(print())
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
    void testGetAll() throws Exception {
        Equipo equipo1 = new Equipo();
        equipo1.setNombre("Racing de Santander");
        equipo1.setMejorJugador("Iñigo Vicente");
        equipo1.setNumeroVictoria(0);
        equipo1.setDescenso(false);

        Equipo equipo2 = new Equipo();
        equipo2.setNombre("Sporting de Gijón");
        equipo2.setMejorJugador("Juan Otero");
        equipo2.setNumeroVictoria(2);
        equipo2.setDescenso(false);

        String equipo1Json = objectMapper.writeValueAsString(equipo1);
        String equipo2Json = objectMapper.writeValueAsString(equipo2); 
        
        // creo los equipos y los meto en base de datos
        mockMvc.perform(post("/equipo")
                .contentType("application/json")
                .content(equipo1Json))
                .andExpect(status().isOk())
                .andReturn();
        
        mockMvc.perform(post("/equipo")
                .contentType("application/json")
                .content(equipo2Json))
                .andExpect(status().isOk())
                .andReturn();
        
        // obtengo la lista a través de una petición get
        MvcResult result = mockMvc.perform(get("/equipo")
                        .contentType("application/json"))
                        .andExpect(status().isOk())
                        .andReturn();

        // deserializo la respuesta
        String respuesta = result.getResponse().getContentAsString(); // convierto el Json de respuesta a String
        Equipo[] arrayEquipos = objectMapper.readValue(respuesta, Equipo[].class);
        assertEquals(arrayEquipos[0].getNombre(), "Racing de Santander");
        assertTrue(arrayEquipos[1].getNumeroVictoria() == 2);
    }

    @Test
    void testUpdate() throws Exception {
        Equipo equipo = new Equipo();
        equipo.setNombre("Racing de Santander");
        equipo.setMejorJugador("Iñigo Vicente");
        equipo.setNumeroVictoria(0);
        equipo.setDescenso(false);

        String equipoJson = objectMapper.writeValueAsString(equipo); // simulo que este es el body de mi petición, de modo que
                                                                     // convierto a String el Equipo que he creado, es decir, lo convierto
                                                                     // a formato Json
        // primeramente, simulo un post para crear un Equipo y retorno la respuesta en formato json
        MvcResult result = mockMvc.perform(post("/equipo")
                                .contentType("application/json")
                                .content(equipoJson))
                                .andExpect(status().isOk())
                                .andReturn();
        
        // obtengo el contenido de la respuesta y la convierto en objeto Equipo
        String respuesta = result.getResponse().getContentAsString(); // convierto el Json de respuesta a String
        Equipo registroCreado = objectMapper.readValue(respuesta, Equipo.class);

        // actualizo los datos que quiero
        registroCreado.setMejorJugador("Andrés Martín");
        registroCreado.setNumeroVictoria(1);

        // serializo de nuevo a Json
        String equipoActualizadoJson = objectMapper.writeValueAsString(registroCreado);

        // con el mismo id que se me creó en la petición post, envío un put donde verifico que, en efecto, la respuesta, pasada a
        // objeto Equipo, tiene los datos actualizados
        mockMvc.perform(put("/equipo/1")
                                .contentType("application/json")
                                .content(equipoActualizadoJson))
                                .andExpect(status().isOk())
                                .andExpect(result2 -> {
                                        String respuesta2 = result2.getResponse().getContentAsString(); // convierto el Json de respuesta a String
                                        Equipo nuevoRegistroCreado = objectMapper.readValue(respuesta2, Equipo.class); // convierto el String respuesta a un objeto de Equipo
                                        assertEquals(nuevoRegistroCreado.getMejorJugador(), "Andrés Martín"); // y verifico que su id no sea null, es decir, que se haya creado
                                        assertTrue(nuevoRegistroCreado.getNumeroVictoria() == 1);

                });
    }

    @Test
    void testUpdateIdsNoCoinciden() throws Exception {
        
        Equipo equipo = new Equipo();
        equipo.setNombre("Racing de Santander");
        equipo.setMejorJugador("Iñigo Vicente");
        equipo.setNumeroVictoria(0);
        equipo.setDescenso(false);

        String equipoJson = objectMapper.writeValueAsString(equipo); // simulo que este es el body de mi petición, de modo que
                                                                     // convierto a String el Equipo que he creado, es decir, lo convierto
                                                                     // a formato Json
        // primeramente, simulo un post para crear un Equipo y retorno la respuesta en formato json
        MvcResult result = mockMvc.perform(post("/equipo")
                                .contentType("application/json")
                                .content(equipoJson))
                                .andExpect(status().isOk())
                                .andReturn();
        
        // obtengo el contenido de la respuesta y la convierto en objeto Equipo
        String respuesta = result.getResponse().getContentAsString(); // convierto el Json de respuesta a String
        Equipo registroCreado = objectMapper.readValue(respuesta, Equipo.class);

        // actualizo los datos que quiero
        registroCreado.setMejorJugador("Andrés Martín");
        registroCreado.setNumeroVictoria(1);

        // serializo de nuevo a Json
        String equipoActualizadoJson = objectMapper.writeValueAsString(registroCreado);

        // con el mismo id que se me creó en la petición post, envío un put donde verifico que, en efecto, la respuesta, pasada a
        // objeto Equipo, tiene los datos actualizados
        mockMvc.perform(put("/equipo/2")
                                .contentType("application/json")
                                .content(equipoActualizadoJson))
                                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateIntentoModificacion() throws Exception {
        
        Equipo equipo = new Equipo();
        equipo.setId(7L); // tengo que poner el id manualmente porque si crease una petición post se me guardaría en base de
                             // datos, y lo que quiero comprobar es precisamente que no puedo actualizar un objeto cuya id, a pesar
                             // de coincidir el del body con el de la cabecera de la petición, no está en base de datos
        equipo.setNombre("Racing de Santander");
        equipo.setMejorJugador("Iñigo Vicente");
        equipo.setNumeroVictoria(0);
        equipo.setDescenso(false);

        String equipoJson = objectMapper.writeValueAsString(equipo);

        mockMvc.perform(put("/equipo/7") // aquí los ids sí que coinciden (este con el de equipoJson)
                                .contentType("application/json")
                                .content(equipoJson))
                                .andExpect(status().isBadRequest());
    }
}
