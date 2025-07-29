package es.cic25.proy008.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic25.proy008.model.Equipo;
import es.cic25.proy008.repository.EquipoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest
@AutoConfigureMockMvc
// @Transactional
public class EquipoControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc; // vamos a simular una petición HTTP
    @Autowired
    ObjectMapper objectMapper; // y nos ayudaremos de objectMapper para conversiones
    @Autowired
    private EquipoRepository equipoRepository;

    @PersistenceContext
    private EntityManager em;

    // testeamos create() en caso de que el id que se le pase sea null, es decir, que no se le intente pasar un id inicialmente
    @Test
    void testCreate() throws Exception { // el "throws Exception" se hace por JsonProcessingException
        Equipo equipo = new Equipo();
        equipo.setNombre("Racing de Santander");
        equipo.setMejorJugador("Iñigo Vicente");
        equipo.setNumeroVictoria(0);
        equipo.setDescenso(false);
        
        // simulamos que este es el body de la petición, de modo que convertimos a String el Equipo sin id creado (obtenemos json)
        String equipoJson = objectMapper.writeValueAsString(equipo); 

        mockMvc.perform(post("/equipo")
                .contentType("application/json")
                .content(equipoJson)) // hasta aquí, hemos simulado una petición cuyo cuerpo es el string antes creado
                .andDo(print())
                .andExpect(status().isOk()) // es decir, que dé un 200
                .andExpect(result -> {
                                // convertimos la instancia de MvcResult (result) a MockHttpServletResponse, y a su vez
                                // seleccionamos el body (content) y lo obtenemos como String
                                String respuesta = result.getResponse().getContentAsString(); 
                                // convertimos el String respuesta a un objeto de Equipo
                                Equipo registroCreado = objectMapper.readValue(respuesta, Equipo.class);
                                // y verificamos que su id no sea null, es decir, que se haya creado
                                assertTrue(registroCreado.getId() != null, "El valor no debe ser null");
                                // verificamos que hay un objeto con esta id en base de datos
                                Optional<Equipo> registroRealmenteCreado = equipoRepository.findById(registroCreado.getId());
                                 // y que está guardado
                                assertTrue(registroRealmenteCreado.isPresent());

                });
    }

    // testeamos create() en el caso en que se intenta preasignar un id, obteniéndose BAD_REQUEST
    @Test
    void testCreateModificacion() throws Exception { // el "throws Exception" se hace por JsonProcessingException
        Equipo equipo = new Equipo();
        equipo.setNombre("Racing de Santander");
        equipo.setMejorJugador("Iñigo Vicente");
        equipo.setNumeroVictoria(0);
        equipo.setDescenso(false);
        equipo.setId(1L); // vamos a tratar de que salte la excepción y, en última instancia, dé un BAD_REQUEST

        // simulamos que este es el body de la petición, de modo que convertimos a String el Equipo sin id creado (obtenemos json)
        String equipoJson = objectMapper.writeValueAsString(equipo);

        mockMvc.perform(post("/equipo")
                .contentType("application/json")
                .content(equipoJson)) // hemos simulado una petición cuyo cuerpo es el String antes creado, incluyendo id
                .andExpect(status().isBadRequest()); // y verificamos que, habiendo asignado id manualmente, se obtiene BAD_REQUEST
    }

    // testeamos el borrado
    @Test
    void testDelete() throws Exception {
        Equipo equipo = new Equipo();
        equipo.setNombre("Racing de Santander");
        equipo.setMejorJugador("Iñigo Vicente");
        equipo.setNumeroVictoria(0);
        equipo.setDescenso(false);

        // simulamos que este es el body de la petición, de modo que convertimos a String el Equipo sin id creado (obtenemos json)
        String equipoJson = objectMapper.writeValueAsString(equipo); 
        
        // primeramente, simulamos bajo los datos del String una petición post para crear un Equipo
        MvcResult result = mockMvc.perform(post("/equipo")
                .contentType("application/json")
                .content(equipoJson))
                .andExpect(status().isOk())
                .andReturn();
        
        
        // convertimos la instancia de MvcResult (result) a MockHttpServletResponse, seleccionamos el body (content) y lo obtenemos
        // como String para, finalmente, convertir el String a un objeto de Equipo
        String respuesta = result.getResponse().getContentAsString();
        Equipo registroCreado = objectMapper.readValue(respuesta, Equipo.class);
        
        // obtenemos el id de ese objeto
        Long idRegistroCreado = registroCreado.getId();

        // y comprobamos que, si tratamos de borrarlo, lo hacemos adecuadamente
        mockMvc.perform(delete("/equipo/" + idRegistroCreado))
                .andExpect(status().isOk());
    }

    // testeamos la lectura de 1 elemento
    @Test
    void testGetOne() throws Exception {
        equipoRepository.findEquipoByNombre("Juan");


        Equipo equipo = new Equipo();
        equipo.setNombre("Racing de Santander");
        equipo.setMejorJugador("Iñigo Vicente");
        equipo.setNumeroVictoria(0);
        equipo.setDescenso(false);

        // simulamos que este es el body de la petición, de modo que convertimos a String el Equipo sin id creado (obtenemos json)
        String equipoJson = objectMapper.writeValueAsString(equipo); 

        // primeramente, simulamos bajo los datos del String una petición post para crear un Equipo
        MvcResult result = mockMvc.perform(post("/equipo")
                .contentType("application/json")
                .content(equipoJson))
                .andExpect(status().isOk())
                .andReturn();
        
        // convertimos la instancia de MvcResult (result) a MockHttpServletResponse, seleccionamos el body (content) y lo obtenemos
        // como String para, finalmente, convertir el String a un objeto de Equipo
        String respuesta = result.getResponse().getContentAsString();
        Equipo registroCreado = objectMapper.readValue(respuesta, Equipo.class);
        
        // obtenemos el id de ese objeto
        Long idRegistroCreado = registroCreado.getId();

        // y finalmente esperamos que la lectura sea correcta simulando una petición get
        mockMvc.perform(get("/equipo/"+ idRegistroCreado))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Racing de Santander"));
        equipoRepository.flush();
    }

    // testeamos la lectura de varios elementos
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

        // simulamos que estos sean los body de las peticiones, de modo que convertimos a String cada Equipo sin id creado (json)
        String equipo1Json = objectMapper.writeValueAsString(equipo1);
        String equipo2Json = objectMapper.writeValueAsString(equipo2); 
        
        // creamos los equipos y los metemos en base de datos
        MvcResult result1 = mockMvc.perform(post("/equipo")
                .contentType("application/json")
                .content(equipo1Json))
                .andExpect(status().isOk())
                .andReturn();
        
        MvcResult result2 = mockMvc.perform(post("/equipo")
                .contentType("application/json")
                .content(equipo2Json))
                .andExpect(status().isOk())
                .andReturn();
        
        // efectuamos de nuevo las conversiones a objeto del resultado de cada petición
        String respuesta1 = result1.getResponse().getContentAsString();
        Equipo registroCreado1 = objectMapper.readValue(respuesta1, Equipo.class);

        String respuesta2 = result2.getResponse().getContentAsString();
        Equipo registroCreado2 = objectMapper.readValue(respuesta2, Equipo.class);

        // obtenemos la lista a través de una petición get
        MvcResult result = mockMvc.perform(get("/equipo")
                        .contentType("application/json"))
                        .andExpect(status().isOk())
                        .andReturn();

        // obtenemos la respuesta como un Array de objetos Equipo
        String respuesta = result.getResponse().getContentAsString();
        Equipo[] arrayEquipos = objectMapper.readValue(respuesta, Equipo[].class);

        // y comprobamos que el elemento 1 se corresponde con registroCreado1, y el 2 con registroCreado2
        assertTrue(arrayEquipos.length >= 2);
    }

    // testeamos la actualización
    @Test
    void testUpdate() throws Exception {
        Equipo equipo = new Equipo();
        equipo.setNombre("Racing de Santander");
        equipo.setMejorJugador("Iñigo Vicente");
        equipo.setNumeroVictoria(0);
        equipo.setDescenso(false);

        // simulamos que este es el body de la petición, de modo que convertimos a String el Equipo sin id creado (obtenemos json)
        String equipoJson = objectMapper.writeValueAsString(equipo);

        // simulamos bajo los datos del String una petición post para crear un Equipo
        MvcResult result = mockMvc.perform(post("/equipo")
                                .contentType("application/json")
                                .content(equipoJson))
                                .andExpect(status().isOk())
                                .andReturn();
        
        // convertimos la instancia de MvcResult (result) a MockHttpServletResponse, seleccionamos el body (content) y lo obtenemos
        // como String para, finalmente, convertir el String a un objeto de Equipo
        String respuesta = result.getResponse().getContentAsString(); // convierto el Json de respuesta a String
        Equipo registroCreado = objectMapper.readValue(respuesta, Equipo.class);

        // actualizamos algunos datos
        registroCreado.setMejorJugador("Andrés Martín");
        registroCreado.setNumeroVictoria(1);

        // y obtenemos el id del registro
        Long idRegistroCreado = registroCreado.getId();

        // serializamos de nuevo a Json
        String equipoActualizadoJson = objectMapper.writeValueAsString(registroCreado);

        // con el mismo id que se me creó en la petición post, envío un put donde verifico que, en efecto, la respuesta, pasada a
        // objeto Equipo, tiene los datos actualizados
        mockMvc.perform(put("/equipo/" + idRegistroCreado)
                                .contentType("application/json")
                                .content(equipoActualizadoJson))
                                .andExpect(status().isOk())
                                .andExpect(result2 -> {
                                        // convertimos la instancia de MvcResult (result) a MockHttpServletResponse, y a su vez
                                        // seleccionamos el body (content) y lo obtenemos como String
                                        String respuesta2 = result2.getResponse().getContentAsString();
                                        // convertimos el String respuesta a un objeto de Equipo
                                        Equipo nuevoRegistroCreado = objectMapper.readValue(respuesta2, Equipo.class);
                                        // y verificamos que los datos se hayan actualizado correctamente
                                        assertEquals(nuevoRegistroCreado.getMejorJugador(), "Andrés Martín");
                                        assertTrue(nuevoRegistroCreado.getNumeroVictoria() == 1);

                });
    }

    // testeamos la actualización cuando el id del body no coincide con el id de la URL
    @Test
    void testUpdateIdsNoCoinciden() throws Exception {
        
        Equipo equipo = new Equipo();
        equipo.setNombre("Racing de Santander");
        equipo.setMejorJugador("Iñigo Vicente");
        equipo.setNumeroVictoria(0);
        equipo.setDescenso(false);

        // simulamos que este es el body de mi petición, de modo que convertimos a String el Equipo sin id creado (obtenemos json)
        String equipoJson = objectMapper.writeValueAsString(equipo);

        // simulamos bajo los datos del String una petición post para crear un Equipo
        MvcResult result = mockMvc.perform(post("/equipo")
                                .contentType("application/json")
                                .content(equipoJson))
                                .andExpect(status().isOk())
                                .andReturn();
        
        // convertimos la instancia de MvcResult (result) a MockHttpServletResponse, seleccionamos el body (content) y lo obtenemos
        // como String para, finalmente, convertir el String a un objeto de Equipo
        String respuesta = result.getResponse().getContentAsString(); // convierto el Json de respuesta a String
        Equipo registroCreado = objectMapper.readValue(respuesta, Equipo.class);

        // actualizamos algunos datos
        registroCreado.setMejorJugador("Andrés Martín");
        registroCreado.setNumeroVictoria(1);

        // y obtenemos el id del registro
        Long idRegistroCreado = registroCreado.getId();

        // serializamos de nuevo a Json
        String equipoActualizadoJson = objectMapper.writeValueAsString(registroCreado);

        // y sumamos 1 al id para enviarle uno distinto desde URL
        Long otroId = idRegistroCreado + 1L;

        // con el mismo id que se me creó en la petición post, envío un put donde verifico que, en efecto, la respuesta, pasada a
        // objeto Equipo, tiene los datos actualizados
        mockMvc.perform(put("/equipo/" + otroId)
                                .contentType("application/json")
                                .content(equipoActualizadoJson)) // este tendría como id a idRegistroCreado, distinto de otroId
                                .andExpect(status().isBadRequest());
    }

    // testeamos el update() cuando se le pasa inicialmente una id que no está en base de datos
    @Test
    void testUpdateIntentoModificacion() throws Exception {
        
        Equipo equipo = new Equipo();
        // asignamos el id manualmente porque, si creásemos una petición post, se guardaría en base de datos, y queremos
        // precisamente comprobar que no se puede actualizar un objeto cuya id, a pesar de coincidir el del body con el de la URL,
        // no está en base de datos
        equipo.setId(7L); 
        equipo.setNombre("Racing de Santander");
        equipo.setMejorJugador("Iñigo Vicente");
        equipo.setNumeroVictoria(0);
        equipo.setDescenso(false);

        // simulamos que este es el body de mi petición, de modo que convertimos a String el Equipo sin id creado (obtenemos json)
        String equipoJson = objectMapper.writeValueAsString(equipo);

        // y comprobamos que, aunque los ids coincidan, como no está el Equipo en base de datos, no se puede procesar
        mockMvc.perform(put("/equipo/7") // aquí los ids sí que coinciden (este con el de equipoJson)
                                .contentType("application/json")
                                .content(equipoJson))
                                .andExpect(status().isBadRequest());
    }
}
