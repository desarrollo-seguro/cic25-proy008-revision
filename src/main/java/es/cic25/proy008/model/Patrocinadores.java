package es.cic25.proy008.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "Patrocinadores")
public class Patrocinadores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Creacion del Id del Patrocinador
    private Long id;

    // Version para el control de concurrencia
    @Version
    private Long version;

    // Creacion de los atributos del Patrocinador
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "numeroPatrocinadores")
    private double numeroPatrocinadores;
    @Column(name = "tipo")
    private String tipo; // Tipo de patrocinador (por ejemplo, "financiero", "técnico", etc.)
    @Column(name = "anosPatrocinio")
    private int anosPatrocinio;

    @JsonIgnore
    @OneToOne(mappedBy = "patrocinador", cascade = CascadeType.REMOVE)
    private Equipo equipo;

    public Patrocinadores() {
    }

    public Patrocinadores(Long id, String nombre, double numeroPatrocinadores, String tipo, int anosPatrocinio) {
        this.id = id;
        this.nombre = nombre;
        this.numeroPatrocinadores = numeroPatrocinadores;
        this.tipo = tipo;
        this.anosPatrocinio = anosPatrocinio;
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

    public double getNumeroPatrocinadores() {
        return numeroPatrocinadores;
    }

    public void setNumeroPatrocinadores(double numeroPatrocinadores) {
        this.numeroPatrocinadores = numeroPatrocinadores;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getAnosPatrocinio() {
        return anosPatrocinio;
    }

    public void setAnosPatrocinio(int anosPatrocinio) {
        this.anosPatrocinio = anosPatrocinio;
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
        Patrocinadores other = (Patrocinadores) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Patrocinadores [id=" + id + ", version=" + version + ", nombre=" + nombre + ", numeroPatrocinadores="
                + numeroPatrocinadores + ", tipo=" + tipo + ", anosPatrocinio=" + anosPatrocinio + "]";
    }

}
