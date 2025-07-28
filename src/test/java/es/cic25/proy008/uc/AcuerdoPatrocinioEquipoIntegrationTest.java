package es.cic25.proy008.uc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic25.proy008.model.Equipo;
import es.cic25.proy008.model.Patrocinador;

@SpringBootTest
@AutoConfigureMockMvc
public class AcuerdoPatrocinioEquipoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    // sé que debería hacer cada test por separado, pero prefiero aprender primero a usar BeforeEach. En caso contrario,
    // quedaría igual de extenso que los test de Controller, y para eso prefiero dejarlo así
    @Test
    public void testEstablecerPatrocinio() throws Exception {
       
        Equipo equipo = new Equipo();
        equipo.setNombre("Racing de Santander");
        equipo.setMejorJugador("Iñigo Vicente");
        equipo.setNumeroVictoria(0);
        equipo.setDescenso(false);


        Patrocinador patrocinadorTest = new Patrocinador();
        patrocinadorTest.setNombre("Plenitude");
        patrocinadorTest.setAñosActivos(3);
        patrocinadorTest.setDaBeneficio(true);
        patrocinadorTest.setProducto("Luz");
        patrocinadorTest.setEquipo(equipo);

        String patrocinadorACrearJson = objectMapper.writeValueAsString(patrocinadorTest);
        
        // testeamos el create() de Equipo, que recibe un patrocinador y lo crea
        MvcResult result = mockMvc.perform(post("/equipo/patrocinio")
                                .contentType("application/json")
                                .content(patrocinadorACrearJson))
                                .andExpect(status().isOk())
                                .andExpect(equipoResult ->{
                                    String respuesta = equipoResult.getResponse().getContentAsString();
                                    Patrocinador nuevoRegistroCreado = objectMapper.readValue(respuesta, Patrocinador.class);
                                    assertNotNull(nuevoRegistroCreado, "Ha habido un acuerdo de patrocinio con el equipo");
                                    })
                                .andReturn();

        String respuesta = result.getResponse().getContentAsString();
        Patrocinador patrocinadorCreado = objectMapper.readValue(respuesta, Patrocinador.class);

        Long idPatrocinadorCreado = patrocinadorCreado.getId();

        // testeamos el get() de Patrocinador a partir del patrocinador que se ha obtenido
        mockMvc.perform(get("/patrocinador/" + idPatrocinadorCreado))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(patrocinadorResult -> {
                    String respuesta2 = patrocinadorResult.getResponse().getContentAsString();
                    Patrocinador nuevoRegistroCreado2 = objectMapper.readValue(respuesta2, Patrocinador.class);
                    Long idNuevoRegistroCreado2 = nuevoRegistroCreado2.getId();
                    assertEquals(idNuevoRegistroCreado2, idPatrocinadorCreado);
                });   
         
        // testeamos ahora el update() de Patrocinador
        patrocinadorCreado.getEquipo().setNumeroVictoria(1);

        String patrocinadorJson = objectMapper.writeValueAsString(patrocinadorCreado);

        mockMvc.perform(put("/patrocinador/" + idPatrocinadorCreado)
                .contentType("application/json")
                .content(patrocinadorJson))
                .andDo(print())                
                .andExpect(status().isOk());

        // y testeamos finalmente el delete() de Patrocinador
        mockMvc.perform(delete("/patrocinador/" + idPatrocinadorCreado))
                .andDo(print())        
                .andExpect(status().isOk());                
    }
}
