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

import es.cic25.proy008.model.Patrocinador;
import es.cic25.proy008.repository.PatrocinadorRepository;
import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PatrocinadorControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc; // vamos a simular una petición HTTP
    @Autowired
    ObjectMapper objectMapper; // y nos ayudaremos de objectMapper para conversiones
    @Autowired
    private PatrocinadorRepository patrocinadorRepository;

    // testeamos create() en caso de que el id que se le pase sea null, es decir, que no se le intente pasar un id inicialmente
    @Test
    void testCreate() throws Exception { // el "throws Exception" se hace por JsonProcessingException
        Patrocinador patrocinador = new Patrocinador();
        patrocinador.setNombre("Plenitude");
        patrocinador.setAñosActivos(3);
        patrocinador.setDaBeneficio(true);
        patrocinador.setProducto("Luz");
        
        // simulamos que este es el body de la petición, de modo que convertimos a String el Equipo sin id creado (obtenemos json)
        String patrocinadorJson = objectMapper.writeValueAsString(patrocinador); 

        mockMvc.perform(post("/patrocinador")
                .contentType("application/json")
                .content(patrocinadorJson)) // hasta aquí, hemos simulado una petición cuyo cuerpo es el string antes creado
                .andDo(print())
                .andExpect(status().isOk()) // es decir, que dé un 200
                .andExpect(result -> {
                                // convertimos la instancia de MvcResult (result) a MockHttpServletResponse, y a su vez
                                // seleccionamos el body (content) y lo obtenemos como String
                                String respuesta = result.getResponse().getContentAsString(); 
                                // convertimos el String respuesta a un objeto de Patrocinador
                                Patrocinador registroCreado = objectMapper.readValue(respuesta, Patrocinador.class);
                                // y verificamos que su id no sea null, es decir, que se haya creado
                                assertTrue(registroCreado.getId() != null, "El valor no debe ser null");
                                // verificamos que hay un objeto con esta id en base de datos
                                Optional<Patrocinador> registroRealmenteCreado = patrocinadorRepository.findById(registroCreado.getId());
                                 // y que está guardado
                                assertTrue(registroRealmenteCreado.isPresent());

                });
    }

    // testeamos create() en el caso en que se intenta preasignar un id, obteniéndose BAD_REQUEST
    @Test
    void testCreateModificacion() throws Exception { // el "throws Exception" se hace por JsonProcessingException
        Patrocinador patrocinador = new Patrocinador();
        patrocinador.setNombre("Plenitude");
        patrocinador.setAñosActivos(3);
        patrocinador.setDaBeneficio(true);
        patrocinador.setProducto("Luz");
        patrocinador.setId(1L); // vamos a tratar de que salte la excepción y, en última instancia, dé un BAD_REQUEST

        // simulamos que este es el body de la petición, de modo que convertimos a String el Patrocinador sin id creado (obtenemos json)
        String patrocinadorJson = objectMapper.writeValueAsString(patrocinador);

        mockMvc.perform(post("/patrocinador")
                .contentType("application/json")
                .content(patrocinadorJson)) // hemos simulado una petición cuyo cuerpo es el String antes creado, incluyendo id
                .andExpect(status().isBadRequest()); // y verificamos que, habiendo asignado id manualmente, se obtiene BAD_REQUEST
    }

    // testeamos el borrado
    @Test
    void testDelete() throws Exception {
        Patrocinador patrocinador = new Patrocinador();
        patrocinador.setNombre("Plenitude");
        patrocinador.setAñosActivos(3);
        patrocinador.setDaBeneficio(true);
        patrocinador.setProducto("Luz");

        // simulamos que este es el body de la petición, de modo que convertimos a String el Patrocinador sin id creado (obtenemos json)
        String patrocinadorJson = objectMapper.writeValueAsString(patrocinador); 
        
        // primeramente, simulamos bajo los datos del String una petición post para crear un Patrocinador
        MvcResult result = mockMvc.perform(post("/patrocinador")
                .contentType("application/json")
                .content(patrocinadorJson))
                .andExpect(status().isOk())
                .andReturn();
        
        
        // convertimos la instancia de MvcResult (result) a MockHttpServletResponse, seleccionamos el body (content) y lo obtenemos
        // como String para, finalmente, convertir el String a un objeto de Patrocinador
        String respuesta = result.getResponse().getContentAsString();
        Patrocinador registroCreado = objectMapper.readValue(respuesta, Patrocinador.class);
        
        // obtenemos el id de ese objeto
        Long idRegistroCreado = registroCreado.getId();

        // y comprobamos que, si tratamos de borrarlo, lo hacemos adecuadamente
        mockMvc.perform(delete("/patrocinador/" + idRegistroCreado))
                .andExpect(status().isOk());
    }

    // testeamos la lectura de 1 elemento
    @Test
    void testGetOne() throws Exception {
        Patrocinador patrocinador = new Patrocinador();
        patrocinador.setNombre("Plenitude");
        patrocinador.setAñosActivos(3);
        patrocinador.setDaBeneficio(true);
        patrocinador.setProducto("Luz");

        // simulamos que este es el body de la petición, de modo que convertimos a String el Patrocinador sin id creado (obtenemos json)
        String patrocinadorJson = objectMapper.writeValueAsString(patrocinador); 

        // primeramente, simulamos bajo los datos del String una petición post para crear un Patrocinador
        MvcResult result = mockMvc.perform(post("/patrocinador")
                .contentType("application/json")
                .content(patrocinadorJson))
                .andExpect(status().isOk())
                .andReturn();
        
        // convertimos la instancia de MvcResult (result) a MockHttpServletResponse, seleccionamos el body (content) y lo obtenemos
        // como String para, finalmente, convertir el String a un objeto de Patrocinador
        String respuesta = result.getResponse().getContentAsString();
        Patrocinador registroCreado = objectMapper.readValue(respuesta, Patrocinador.class);
        
        // obtenemos el id de ese objeto
        Long idRegistroCreado = registroCreado.getId();

        // y finalmente esperamos que la lectura sea correcta simulando una petición get
        mockMvc.perform(get("/patrocinador/"+ idRegistroCreado))
                .andExpect(status().isOk());
    }

    // testeamos la lectura de varios elementos
    @Test
    void testGetAll() throws Exception {
        Patrocinador patrocinador1 = new Patrocinador();
        patrocinador1.setNombre("Plenitude");
        patrocinador1.setAñosActivos(3);
        patrocinador1.setDaBeneficio(true);
        patrocinador1.setProducto("Luz");

        Patrocinador patrocinador2 = new Patrocinador();
        patrocinador2.setNombre("Qatar Airways");
        patrocinador2.setAñosActivos(10);
        patrocinador2.setDaBeneficio(true);
        patrocinador2.setProducto("Vuelos");

        // simulamos que estos sean los body de las peticiones, de modo que convertimos a String cada Equipo sin id creado (json)
        String patrocinador1Json = objectMapper.writeValueAsString(patrocinador1);
        String patrocinador2Json = objectMapper.writeValueAsString(patrocinador2); 
        
        // creamos los equipos y los metemos en base de datos
        MvcResult result1 = mockMvc.perform(post("/patrocinador")
                .contentType("application/json")
                .content(patrocinador1Json))
                .andExpect(status().isOk())
                .andReturn();
        
        MvcResult result2 = mockMvc.perform(post("/patrocinador")
                .contentType("application/json")
                .content(patrocinador2Json))
                .andExpect(status().isOk())
                .andReturn();
        
        // efectuamos de nuevo las conversiones a objeto del resultado de cada petición
        String respuesta1 = result1.getResponse().getContentAsString();
        Patrocinador registroCreado1 = objectMapper.readValue(respuesta1, Patrocinador.class);

        String respuesta2 = result2.getResponse().getContentAsString();
        Patrocinador registroCreado2 = objectMapper.readValue(respuesta2, Patrocinador.class);

        // obtenemos la lista a través de una petición get
        MvcResult result = mockMvc.perform(get("/patrocinador")
                        .contentType("application/json"))
                        .andExpect(status().isOk())
                        .andReturn();

        // obtenemos la respuesta como un Array de objetos Patrocinador
        String respuesta = result.getResponse().getContentAsString();
        Patrocinador[] arrayPatrocinadores = objectMapper.readValue(respuesta, Patrocinador[].class);

        // y comprobamos que el elemento 1 se corresponde con registroCreado1, y el 2 con registroCreado2
        assertEquals(arrayPatrocinadores[0], registroCreado1);
        assertEquals(arrayPatrocinadores[1], registroCreado2);
    }

    // testeamos la actualización
    @Test
    void testUpdate() throws Exception {
        Patrocinador patrocinador = new Patrocinador();
        patrocinador.setNombre("Plenitude");
        patrocinador.setAñosActivos(3);
        patrocinador.setDaBeneficio(true);
        patrocinador.setProducto("Luz");

        // simulamos que este es el body de la petición, de modo que convertimos a String el Patrocinador sin id creado (obtenemos json)
        String patrocinadorJson = objectMapper.writeValueAsString(patrocinador);

        // simulamos bajo los datos del String una petición post para crear un Patrocinador
        MvcResult result = mockMvc.perform(post("/patrocinador")
                                .contentType("application/json")
                                .content(patrocinadorJson))
                                .andExpect(status().isOk())
                                .andReturn();
        
        // convertimos la instancia de MvcResult (result) a MockHttpServletResponse, seleccionamos el body (content) y lo obtenemos
        // como String para, finalmente, convertir el String a un objeto de Patrocinador
        String respuesta = result.getResponse().getContentAsString(); // convierto el Json de respuesta a String
        Patrocinador registroCreado = objectMapper.readValue(respuesta, Patrocinador.class);

        // actualizamos algunos datos
        registroCreado.setAñosActivos(4);
        registroCreado.setProducto("Luz y gas");

        // y obtenemos el id del registro
        Long idRegistroCreado = registroCreado.getId();

        // serializamos de nuevo a Json
        String patrocinadorActualizadoJson = objectMapper.writeValueAsString(registroCreado);

        // con el mismo id que se me creó en la petición post, envío un put donde verifico que, en efecto, la respuesta, pasada a
        // objeto Patrocinador, tiene los datos actualizados
        mockMvc.perform(put("/patrocinador/" + idRegistroCreado)
                                .contentType("application/json")
                                .content(patrocinadorActualizadoJson))
                                .andExpect(status().isOk())
                                .andExpect(result2 -> {
                                        // convertimos la instancia de MvcResult (result) a MockHttpServletResponse, y a su vez
                                        // seleccionamos el body (content) y lo obtenemos como String
                                        String respuesta2 = result2.getResponse().getContentAsString();
                                        // convertimos el String respuesta a un objeto de Patrocinador
                                        Patrocinador nuevoRegistroCreado = objectMapper.readValue(respuesta2, Patrocinador.class);
                                        // y verificamos que los datos se hayan actualizado correctamente
                                        assertEquals(nuevoRegistroCreado.getProducto(), "Luz y gas");
                                        assertTrue(nuevoRegistroCreado.getAñosActivos() == 4);

                });
    }

    // testeamos la actualización cuando el id del body no coincide con el id de la URL
    @Test
    void testUpdateIdsNoCoinciden() throws Exception {
        
        Patrocinador patrocinador = new Patrocinador();
        patrocinador.setNombre("Plenitude");
        patrocinador.setAñosActivos(3);
        patrocinador.setDaBeneficio(true);
        patrocinador.setProducto("Luz");

        // simulamos que este es el body de mi petición, de modo que convertimos a String el Patrocinador sin id creado (obtenemos json)
        String patrocinadorJson = objectMapper.writeValueAsString(patrocinador);

        // simulamos bajo los datos del String una petición post para crear un Patrocinador
        MvcResult result = mockMvc.perform(post("/patrocinador")
                                .contentType("application/json")
                                .content(patrocinadorJson))
                                .andExpect(status().isOk())
                                .andReturn();
        
        // convertimos la instancia de MvcResult (result) a MockHttpServletResponse, seleccionamos el body (content) y lo obtenemos
        // como String para, finalmente, convertir el String a un objeto de Patrocinador
        String respuesta = result.getResponse().getContentAsString(); // convierto el Json de respuesta a String
        Patrocinador registroCreado = objectMapper.readValue(respuesta, Patrocinador.class);

        // actualizamos algunos datos
        registroCreado.setAñosActivos(4);
        registroCreado.setProducto("Luz y gas");

        // y obtenemos el id del registro
        Long idRegistroCreado = registroCreado.getId();

        // serializamos de nuevo a Json
        String patrocinadorActualizadoJson = objectMapper.writeValueAsString(registroCreado);

        // y sumamos 1 al id para enviarle uno distinto desde URL
        Long otroId = idRegistroCreado + 1L;

        // con el mismo id que se me creó en la petición post, envío un put donde verifico que, en efecto, la respuesta, pasada a
        // objeto Patrocinador, tiene los datos actualizados
        mockMvc.perform(put("/patrocinador/" + otroId)
                                .contentType("application/json")
                                .content(patrocinadorActualizadoJson)) // este tendría como id a idRegistroCreado, distinto de otroId
                                .andExpect(status().isBadRequest());
    }

    // testeamos el update() cuando se le pasa inicialmente una id que no está en base de datos
    @Test
    void testUpdateIntentoModificacion() throws Exception {
        
        Patrocinador patrocinador = new Patrocinador();
        // asignamos el id manualmente porque, si creásemos una petición post, se guardaría en base de datos, y queremos
        // precisamente comprobar que no se puede actualizar un objeto cuya id, a pesar de coincidir el del body con el de la URL,
        // no está en base de datos
        patrocinador.setId(7L); 
        patrocinador.setNombre("Plenitude");
        patrocinador.setAñosActivos(3);
        patrocinador.setDaBeneficio(true);
        patrocinador.setProducto("Luz");

        // simulamos que este es el body de mi petición, de modo que convertimos a String el Patrocinador sin id creado (obtenemos json)
        String patrocinadorJson = objectMapper.writeValueAsString(patrocinador);

        // y comprobamos que, aunque los ids coincidan, como no está el Patrocinador en base de datos, no se puede procesar
        mockMvc.perform(put("/patrocinador/7") // aquí los ids sí que coinciden (este con el de patrocinadorJson)
                                .contentType("application/json")
                                .content(patrocinadorJson))
                                .andExpect(status().isBadRequest());
    }
}
