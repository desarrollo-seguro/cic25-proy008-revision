package es.cic25.proy008.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

import es.cic25.proy008.model.Equipo;

public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    @Query(name = "pepe")
    public List<Equipo> findEquipoByNombre(String nombre);

}
