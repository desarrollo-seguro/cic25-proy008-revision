package es.cic25.proy008.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.cic25.proy008.model.Equipo;
import es.cic25.proy008.model.Patrocinador;

public interface PatrocinadorRepository extends JpaRepository<Patrocinador, Long>{

}
