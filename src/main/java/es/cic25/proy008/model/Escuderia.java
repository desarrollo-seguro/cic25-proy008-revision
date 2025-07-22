package es.cic25.proy008.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "Escuderias") // Define la tabla en la base de datos
public class Escuderia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Creacion del Id de la Escuderia
    private Long id;

    // Version para el control de concurrencia
    @Version
    private Long version;

    // Creacion de los atributos de la Escuderia
    // El @Column indica que este campo es una columna en la tabla de la base de datos
    @Column(name = "nombre") 
    private String nombre;
    @Column(name = "numeroVictorias") 
    private String numeroVictorias; // Cambiar numeroVictoria a numeroVictorias
    @Column(name = "numeroPilotos") 
    private String numeroPilotos; // Cambiar numeroPiloto a numeroPilotos
    @Column(name = "color") 
    private String color;

    // Metodos Getters y Setters deL Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Metodos Getters y Setters deL Id
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Metodos Getters y Setters deL Id
    public String getNumeroVictorias() {
        return numeroVictorias;
    }

    public void setNumeroVictorias(String numeroVictorias) {
        this.numeroVictorias = numeroVictorias;
    }

    // Metodos Getters y Setters deL Id
    public String getNumeroPilotos() {
        return numeroPilotos;
    }

    public void setNumeroPilotos(String numeroPilotos) {
        this.numeroPilotos = numeroPilotos;
    }

    // Metodos Getters y Setters deL Id
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
