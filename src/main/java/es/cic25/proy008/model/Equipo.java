package es.cic25.proy008.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;

@Entity
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "numeroVictorias")
    @Max(value = 35)
    private int numeroVictoria;

    @Column(name = "descenso")
    private boolean descenso;

    @Column(name = "mejorJugador")
    private String mejorJugador;

    // aquí cortamos la relación. Decimos: "como cuando se crea un objeto del dueño de la relación (Patrocinador), también se crea
    // uno del que no es dueño (Equipo), no me serialices el objeto de la clase que no es dueña (es decir, Equipo), o entraremos en
    // un bucle infinito"
    @JsonIgnore
    @OneToOne(mappedBy = "equipo", cascade = CascadeType.REMOVE)
    private Patrocinador patrocinador;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}
                )
    private List<Mascota> mascotas = new ArrayList<>();


    public Equipo() {

    }

    public Equipo(Long id, String nombre, int numeroVictoria, boolean descenso, String mejorJugador, Equipo equipo) {
        this.id = id;
        this.nombre = nombre;
        this.numeroVictoria = numeroVictoria;
        this.descenso = descenso;
        this.mejorJugador = mejorJugador;
        this.patrocinador = patrocinador;
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
    public Patrocinador getPatrocinador() {
        return patrocinador;
    }
    public void setPatrocinador(Patrocinador patrocinador) {
        this.patrocinador = patrocinador;
    }


    public List<Mascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(List<Mascota> mascotas) {
        this.mascotas = mascotas;
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
