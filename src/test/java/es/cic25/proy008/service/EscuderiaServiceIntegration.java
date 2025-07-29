package es.cic25.proy008.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import es.cic25.proy008.model.Escuderia;
import es.cic25.proy008.model.Motor;
import es.cic25.proy008.repository.EscuderiaRepository;
import es.cic25.proy008.repository.MotorRepository;

/**
 * Pruebas de integración para EscuderiaService.
 * Verifica la correcta interacción entre el servicio y la base de datos.
 */
@SpringBootTest
@Transactional
class EscuderiaServiceIntegration {

    @Autowired
    private EscuderiaService escuderiaService;

    @Autowired
    private EscuderiaRepository escuderiaRepository;

    @Autowired
    private MotorRepository motorRepository;

    @BeforeEach
    void setUp() {
        escuderiaRepository.deleteAll();
        motorRepository.deleteAll();
    }

    @Test
    @DisplayName("create() debe persistir una nueva escudería y retornar su ID")
    void shouldCreateEscuderia() {
        // Preparación
        Motor motor = new Motor();
        motor.setFabricante("Ferrari");
        motor.setPotencia(1000);
        motor.setTipo("V6 Híbrido");
        motor.setAnosUso(1);
        motor = motorRepository.save(motor);

        Escuderia escuderia = new Escuderia();
        escuderia.setNombre("Ferrari");
        escuderia.setNumeroVictorias("238");
        escuderia.setNumeroPilotos("2");
        escuderia.setColor("Rojo");
        escuderia.setMotor(motor);

        // Ejecución
        long id = escuderiaService.create(escuderia);

        // Verificación
        assertTrue(id > 0, "El ID debería ser positivo");
        assertTrue(escuderiaRepository.findById(id).isPresent(), 
            "La escudería debería existir en la base de datos");
    }

    @Test
    @DisplayName("get() debe retornar la escudería si existe")
    void shouldGetExistingEscuderia() {
        // Preparación
        Motor motor = new Motor();
        motor.setFabricante("Mercedes");
        motor.setPotencia(980);
        motor.setTipo("V6 Híbrido");
        motor.setAnosUso(2);
        motor = motorRepository.save(motor);

        Escuderia escuderia = new Escuderia();
        escuderia.setNombre("Mercedes");
        escuderia.setNumeroVictorias("125");
        escuderia.setNumeroPilotos("2");
        escuderia.setColor("Plata");
        escuderia.setMotor(motor);
        escuderia = escuderiaRepository.save(escuderia);

        // Ejecución
        Escuderia resultado = escuderiaService.get(escuderia.getId());

        // Verificación
        assertNotNull(resultado, "La escudería no debería ser null");
        assertEquals("Mercedes", resultado.getNombre(), "El nombre debería coincidir");
        assertEquals("125", resultado.getNumeroVictorias(), "El número de victorias debería coincidir");
        assertEquals("Plata", resultado.getColor(), "El color debería coincidir");
    }

    @Test
    @DisplayName("get() debe retornar todas las escuderías")
    void shouldGetAllEscuderias() {
        // Preparación
        Motor motor1 = new Motor();
        motor1.setFabricante("McLaren");
        motor1.setPotencia(950);
        motor1.setTipo("V6 Híbrido");
        motor1.setAnosUso(2);
        motor1 = motorRepository.save(motor1);

        Motor motor2 = new Motor();
        motor2.setFabricante("Red Bull");
        motor2.setPotencia(970);
        motor2.setTipo("V6 Híbrido");
        motor2.setAnosUso(1);
        motor2 = motorRepository.save(motor2);

        Escuderia escuderia1 = new Escuderia();
        escuderia1.setNombre("McLaren");
        escuderia1.setNumeroVictorias("183");
        escuderia1.setNumeroPilotos("2");
        escuderia1.setColor("Naranja");
        escuderia1.setMotor(motor1);
        escuderiaRepository.save(escuderia1);

        Escuderia escuderia2 = new Escuderia();
        escuderia2.setNombre("Red Bull");
        escuderia2.setNumeroVictorias("113");
        escuderia2.setNumeroPilotos("2");
        escuderia2.setColor("Azul");
        escuderia2.setMotor(motor2);
        escuderiaRepository.save(escuderia2);

        // Ejecución
        List<Escuderia> resultado = escuderiaService.get();

        // Verificación
        assertEquals(2, resultado.size(), "Deberían haber 2 escuderías");
    }

    @Test
    @DisplayName("delete() debe eliminar la escudería existente")
    void shouldDeleteEscuderia() {
        // Preparación
        Motor motor = new Motor();
        motor.setFabricante("Alpine");
        motor.setPotencia(900);
        motor.setTipo("V6 Híbrido");
        motor.setAnosUso(3);
        motor = motorRepository.save(motor);

        Escuderia escuderia = new Escuderia();
        escuderia.setNombre("Alpine");
        escuderia.setNumeroVictorias("21");
        escuderia.setNumeroPilotos("2");
        escuderia.setColor("Azul");
        escuderia.setMotor(motor);
        escuderia = escuderiaRepository.save(escuderia);
        Long id = escuderia.getId();

        // Ejecución
        escuderiaService.delete(id);

        // Verificación
        assertFalse(escuderiaRepository.findById(id).isPresent(), 
            "La escudería no debería existir después de eliminarla");
    }

    @Test
    @DisplayName("update() debe modificar una escudería existente")
    void shouldUpdateEscuderia() {
        // Preparación
        Motor motor = new Motor();
        motor.setFabricante("Williams");
        motor.setPotencia(870);
        motor.setTipo("V6 Híbrido");
        motor.setAnosUso(4);
        motor = motorRepository.save(motor);

        Escuderia escuderia = new Escuderia();
        escuderia.setNombre("Williams");
        escuderia.setNumeroVictorias("114");
        escuderia.setNumeroPilotos("2");
        escuderia.setColor("Azul");
        escuderia.setMotor(motor);
        escuderia = escuderiaRepository.save(escuderia);

        // Modificamos la escudería
        escuderia.setNumeroVictorias("115");
        escuderia.setColor("Blanco");

        // Ejecución
        escuderiaService.update(escuderia);

        // Verificación
        Escuderia actualizada = escuderiaRepository.findById(escuderia.getId()).orElseThrow();
        assertEquals("115", actualizada.getNumeroVictorias(), "El número de victorias debería estar actualizado");
        assertEquals("Blanco", actualizada.getColor(), "El color debería estar actualizado");
    }
}
