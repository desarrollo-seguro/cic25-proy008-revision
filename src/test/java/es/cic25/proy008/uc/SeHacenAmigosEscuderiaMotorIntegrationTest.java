package es.cic25.proy008.uc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic25.proy008.model.Escuderia;
import es.cic25.proy008.model.Motor;

@SpringBootTest
@AutoConfigureMockMvc
public class SeHacenAmigosEscuderiaMotorIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testEstablecerAmistadEscuderiaMotor() throws Exception {
        // Creo primero una escudería
        Escuderia escuderia = new Escuderia();
        escuderia.setNombre("Amigos F1");
        escuderia.setNumeroVictorias("0");
        escuderia.setNumeroPilotos("2");
        escuderia.setColor("Verde");

        // Creo un motor
        Motor motor = new Motor();
        motor.setFabricante("Honda");
        motor.setPotencia(950);
        motor.setTipo("V6 Híbrido");
        motor.setAnosUso(2);

        // Aquí podrías asociar el motor a la escudería si tienes esa relación en tu modelo
        // Por ejemplo: escuderia.setMotor(motor);

        // Convertimos el objeto escuderia en JSON
        String escuderiaJson = objectMapper.writeValueAsString(escuderia);

        // Simulamos la petición HTTP para crear la escudería (puedes adaptar el endpoint si tienes uno específico para la relación)
        mockMvc.perform(post("/escuderias")
                .contentType("application/json")
                .content(escuderiaJson))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(result -> {
                assertNotNull(
                    objectMapper.readValue(
                        result.getResponse().getContentAsString(), Long.class),
                    "La escudería debería haberse creado y devuelto su id");
            });
    }
}
