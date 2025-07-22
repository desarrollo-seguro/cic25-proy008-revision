package es.cic25.proy008.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic25.proy008.model.Escuderia;
import es.cic25.proy008.repository.EscuderiaRepository;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class EscuderiaControllerIntegration {

    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    ObjectMapper objectMapper;
    
    @Autowired
    EscuderiaRepository escuderiaRepository;

    @BeforeEach
    void clearDb() {
        escuderiaRepository.deleteAll();
    }

    @Test
    @DisplayName("POST /escuderias guarda la escudería y devuelve JSON con id")
    void shouldCreateEscuderia() throws Exception {
        Escuderia escuderia = new Escuderia();
        escuderia.setNombre("Ferrari");
        escuderia.setNumeroVictorias("238");
        escuderia.setNumeroPilotos("2");
        escuderia.setColor("Rojo");

        String json = objectMapper.writeValueAsString(escuderia);

        MvcResult res = mockMvc.perform(post("/escuderias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn();

        Long id = objectMapper.readValue(res.getResponse().getContentAsString(), Long.class);
        assertTrue(id > 0, "El id debe ser positivo");

        Optional<Escuderia> enBd = escuderiaRepository.findById(id);
        assertTrue(enBd.isPresent(), "La escudería no se encontró en la base de datos");
    }

    //Test para verificar la obtención de una escudería por su ID
    @Test
    @DisplayName("GET /escuderias/{id} devuelve objeto cuando existe")
    void shouldReturnEscuderia() throws Exception {
        // PREPARACIÓN: Creamos una escudería de prueba
        Escuderia escuderia = new Escuderia();
        escuderia.setNombre("Mercedes");
        escuderia.setNumeroVictorias("125");
        escuderia.setNumeroPilotos("2");
        escuderia.setColor("Plata");

        // Guardamos la escuderia en la base de datos
        escuderia = escuderiaRepository.save(escuderia);

        // EJECUCIÓN Y VERIFICACIÓN: Realizamos la petición GET y verificamos la respuesta
        mockMvc.perform(get("/escuderias/{id}", escuderia.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(escuderia.getId()))
                .andExpect(jsonPath("$.nombre").value("Mercedes"));
    }

   
    //   Test para verificar la eliminación de una escudería
    @Test
    @DisplayName("DELETE /escuderias/{id} elimina el registro")
    void shouldDeleteEscuderia() throws Exception {
        // PREPARACIÓN: Creamos una escudería de prueba
        Escuderia escuderia = new Escuderia();
        escuderia.setNombre("McLaren");
        escuderia.setNumeroVictorias("183");
        escuderia.setNumeroPilotos("2");
        escuderia.setColor("Naranja");

        // Guardamos la escuderia en la base de datos
        escuderia = escuderiaRepository.save(escuderia);

        // EJECUCIÓN: Realizamos la petición DELETE
        mockMvc.perform(delete("/escuderias/{id}", escuderia.getId()))
                .andExpect(status().isOk());

        // VERIFICACIÓN: Comprobamos que la escudería ya no existe
        Optional<Escuderia> enBd = escuderiaRepository.findById(escuderia.getId());
        assertTrue(enBd.isEmpty(), "La escudería debería haber sido eliminada");
    }
}
