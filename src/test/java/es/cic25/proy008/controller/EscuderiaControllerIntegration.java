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
import es.cic25.proy008.model.Motor;
import es.cic25.proy008.repository.EscuderiaRepository;
import es.cic25.proy008.repository.MotorRepository;

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

    @Autowired
    MotorRepository motorRepository;

    // Limpia la base de datos antes de cada test para asegurar un entorno limpio
    @BeforeEach
    void clearDb() {
        escuderiaRepository.deleteAll();
    }

    /**
     * Test para verificar que el endpoint POST /escuderias
     * guarda correctamente una escudería y devuelve su id.
     */
    @Test
    @DisplayName("POST /escuderias guarda la escudería y devuelve JSON con id")
    void shouldCreateEscuderia() throws Exception {
        // Crear motor de prueba
        Motor motor = new Motor();
        motor.setFabricante("Ferrari");
        motor.setPotencia(1000);
        motor.setTipo("V6 Híbrido");
        motor.setAnosUso(1);
        motor = motorRepository.save(motor);

        // Crear objeto escuderia de prueba
        Escuderia escuderia = new Escuderia();
        escuderia.setNombre("Ferrari");
        escuderia.setNumeroVictorias("238");
        escuderia.setNumeroPilotos("2");
        escuderia.setColor("Rojo");
        escuderia.setMotor(motor);

        String json = objectMapper.writeValueAsString(escuderia);

        // Realizar la petición POST y verificar el resultado
        MvcResult res = mockMvc.perform(post("/escuderias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn();

        // Comprobar que el id devuelto es válido y que la escudería se guardó en la base de datos
        Long id = objectMapper.readValue(res.getResponse().getContentAsString(), Long.class);
        assertTrue(id > 0, "El id debe ser positivo");

        Optional<Escuderia> enBd = escuderiaRepository.findById(id);
        assertTrue(enBd.isPresent(), "La escudería no se encontró en la base de datos");
    }

    /**
     * Test para verificar que el endpoint GET /escuderias/{id}
     * devuelve la escudería correspondiente cuando existe.
     */
    @Test
    @DisplayName("GET /escuderias/{id} devuelve objeto cuando existe")
    void shouldReturnEscuderia() throws Exception {
        // Crear motor de prueba
        Motor motor = new Motor();
        motor.setFabricante("Mercedes");
        motor.setPotencia(980);
        motor.setTipo("V6 Híbrido");
        motor.setAnosUso(2);
        motor = motorRepository.save(motor);

        // PREPARACIÓN: Creamos una escudería de prueba
        Escuderia escuderia = new Escuderia();
        escuderia.setNombre("Mercedes");
        escuderia.setNumeroVictorias("125");
        escuderia.setNumeroPilotos("2");
        escuderia.setColor("Plata");
        escuderia.setMotor(motor);

        // Guardamos la escuderia en la base de datos
        escuderia = escuderiaRepository.save(escuderia);

        // EJECUCIÓN Y VERIFICACIÓN: Realizamos la petición GET y verificamos la respuesta
        mockMvc.perform(get("/escuderias/{id}", escuderia.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(escuderia.getId()))
                .andExpect(jsonPath("$.nombre").value("Mercedes"));
    }

    /**
     * Test para verificar que el endpoint GET /escuderias
     * devuelve la lista de todas las escuderías.
     */
    @Test
    @DisplayName("GET /escuderias devuelve la lista de todas las escuderías")
    void shouldReturnAllEscuderias() throws Exception {
        // Crear y guardar dos motores de prueba
        Motor motor1 = new Motor();
        motor1.setFabricante("Honda");
        motor1.setPotencia(950);
        motor1.setTipo("V6 Híbrido");
        motor1.setAnosUso(2);
        motor1 = motorRepository.save(motor1);

        Motor motor2 = new Motor();
        motor2.setFabricante("Renault");
        motor2.setPotencia(900);
        motor2.setTipo("V6 Híbrido");
        motor2.setAnosUso(3);
        motor2 = motorRepository.save(motor2);

        // Crear y guardar dos escuderías de prueba
        Escuderia escuderia1 = new Escuderia();
        escuderia1.setNombre("Red Bull");
        escuderia1.setNumeroVictorias("113");
        escuderia1.setNumeroPilotos("2");
        escuderia1.setColor("Azul");
        escuderia1.setMotor(motor1);
        escuderiaRepository.save(escuderia1);

        Escuderia escuderia2 = new Escuderia();
        escuderia2.setNombre("Alpine");
        escuderia2.setNumeroVictorias("21");
        escuderia2.setNumeroPilotos("2");
        escuderia2.setColor("Azul");
        escuderia2.setMotor(motor2);
        escuderiaRepository.save(escuderia2);

        // Realizar la petición GET y verificar que se devuelven ambas escuderías
        mockMvc.perform(get("/escuderias"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].nombre").value("Red Bull"))
            .andExpect(jsonPath("$[1].nombre").value("Alpine"));
    }

    /**
     * Test para verificar que el endpoint PUT /escuderias
     * actualiza correctamente una escudería existente.
     */
    @Test
    @DisplayName("PUT /escuderias actualiza una escudería existente")
    void shouldUpdateEscuderia() throws Exception {
        // Crear motor de prueba
        Motor motor = new Motor();
        motor.setFabricante("Williams");
        motor.setPotencia(870);
        motor.setTipo("V6 Híbrido");
        motor.setAnosUso(4);
        motor = motorRepository.save(motor);

        // Crear y guardar una escudería de prueba
        Escuderia escuderia = new Escuderia();
        escuderia.setNombre("Williams");
        escuderia.setNumeroVictorias("114");
        escuderia.setNumeroPilotos("2");
        escuderia.setColor("Azul");
        escuderia.setMotor(motor);
        escuderia = escuderiaRepository.save(escuderia);

        // Modificamos algunos campos
        escuderia.setNumeroVictorias("115");
        escuderia.setColor("Blanco");

        String json = objectMapper.writeValueAsString(escuderia);

        // Realizar la petición PUT y verificar el resultado
        mockMvc.perform(put("/escuderias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk());

        // Verificar que los cambios se guardaron en la base de datos
        Escuderia actualizada = escuderiaRepository.findById(escuderia.getId()).orElseThrow();
        assertEquals("115", actualizada.getNumeroVictorias());
        assertEquals("Blanco", actualizada.getColor());
    }

    /**
     * Test para verificar que el endpoint DELETE /escuderias/{id}
     * elimina correctamente una escudería existente.
     */
    @Test
    @DisplayName("DELETE /escuderias/{id} elimina el registro")
    void shouldDeleteEscuderia() throws Exception {
        // Crear motor de prueba
        Motor motor = new Motor();
        motor.setFabricante("McLaren");
        motor.setPotencia(930);
        motor.setTipo("V6 Híbrido");
        motor.setAnosUso(2);
        motor = motorRepository.save(motor);

        // PREPARACIÓN: Creamos una escudería de prueba
        Escuderia escuderia = new Escuderia();
        escuderia.setNombre("McLaren");
        escuderia.setNumeroVictorias("183");
        escuderia.setNumeroPilotos("2");
        escuderia.setColor("Naranja");
        escuderia.setMotor(motor);

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
