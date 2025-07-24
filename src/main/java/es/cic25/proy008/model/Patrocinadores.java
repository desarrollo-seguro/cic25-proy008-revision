package es.cic25.proy008.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    } // Años de patrocinio
    
}
