package es.cic25.proy008.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "numeroVictorias")
    private int numeroVictoria;

    @Column(name = "descenso")
    private boolean descenso;

    @Column(name = "mejorJugador")
    private String mejorJugador;

    @JsonIgnore
    @OneToOne(mappedBy = "equipo")
    private Equipo equipo;

    public Equipo() {

    }

    public Equipo(Long id, String nombre, int numeroVictoria, boolean descenso, String mejorJugador, Equipo equipo) {
        this.id = id;
        this.nombre = nombre;
        this.numeroVictoria = numeroVictoria;
        this.descenso = descenso;
        this.mejorJugador = mejorJugador;
        this.equipo = equipo;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getNumeroVictoria() {
        return numeroVictoria;
    }
    public void setNumeroVictoria(int numeroVictoria) {
        this.numeroVictoria = numeroVictoria;
    }
    public boolean isDescenso() {
        return descenso;
    }
    public void setDescenso(boolean descenso) {
        this.descenso = descenso;
    }
    public String getMejorJugador() {
        return mejorJugador;
    }
    public void setMejorJugador(String mejorJugador) {
        this.mejorJugador = mejorJugador;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Equipo other = (Equipo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "Equipo [id=" + id + ", nombre=" + nombre + ", numeroVictoria=" + numeroVictoria + ", descenso="
                + descenso + ", mejorJugador=" + mejorJugador + "]";
    }

    
}
