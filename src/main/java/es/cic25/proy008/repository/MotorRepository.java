package es.cic25.proy008.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import es.cic25.proy008.model.Motor;

public interface MotorRepository extends JpaRepository<Motor, Long> {
   

}