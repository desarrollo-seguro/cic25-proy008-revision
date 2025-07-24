package es.cic25.proy008.uc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic25.proy008.model.Escuderia;
import es.cic25.proy008.model.Patrocinadores;

@SpringBootTest
@AutoConfigureMockMvc
public class SeHacenAmigosEscuderiaIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testEstablecerAmistadEscuderiaPatrocinador() throws Exception {
        // Creo primero una escudería
        Escuderia escuderia = new Escuderia();
        escuderia.setNombre("Amigos F1");
        escuderia.setNumeroVictorias("0");
        escuderia.setNumeroPilotos("2");
        escuderia.setColor("Verde");

        // Creo un patrocinador
        Patrocinadores patrocinador = new Patrocinadores();
        patrocinador.setNombre("SponsorX");
        patrocinador.setNumeroPatrocinadores(1);
        patrocinador.setTipo("Financiero");
        patrocinador.setAnosPatrocinio(3);

        // Aquí podrías asociar el patrocinador a la escudería si tienes esa relación en tu modelo
        // Por ejemplo: escuderia.setPatrocinador(patrocinador);

        // Convertimos el objeto escuderia en JSON
        String escuderiaJson = objectMapper.writeValueAsString(escuderia);

        // Simulamos la petición HTTP para crear la escudería (puedes adaptar el endpoint si tienes uno específico para la relación)
        mockMvc.perform(post("/escuderias")
                .contentType("application/json")
                .content(escuderiaJson))
            .andExpect(status().isOk())
            .andExpect(result -> {
                assertNotNull(
                    objectMapper.readValue(
                        result.getResponse().getContentAsString(), Long.class),
                    "La escudería debería haberse creado y devuelto su id");
            });
    }
}
