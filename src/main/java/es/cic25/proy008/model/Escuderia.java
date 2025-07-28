package es.cic25.proy008.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "motor_id")
    private Motor motor;

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

    public Motor getMotor() {
        return motor;
    }

    public void setMotor(Motor motor) {
        this.motor = motor;
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
        Escuderia other = (Escuderia) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Escuderia [id=" + id + ", version=" + version + ", nombre=" + nombre + ", numeroVictorias="
                + numeroVictorias + ", numeroPilotos=" + numeroPilotos + ", color=" + color + "]";
    }

    
}
