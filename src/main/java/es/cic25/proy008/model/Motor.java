package es.cic25.proy008.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "Motores")
public class Motor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @Column(name = "fabricante")
    private String fabricante;

    @Column(name = "potencia")
    private int potencia; // en caballos de fuerza

    @Column(name = "tipo")
    private String tipo; // por ejemplo, "V6", "V8", "Híbrido", etc.

    @Column(name = "anosUso")
    private int anosUso;

    public Motor() {
    }

    public Motor(Long id, String fabricante, int potencia, String tipo, int anosUso) {
        this.id = id;
        this.fabricante = fabricante;
        this.potencia = potencia;
        this.tipo = tipo;
        this.anosUso = anosUso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getAnosUso() {
        return anosUso;
    }

    public void setAnosUso(int anosUso) {
        this.anosUso = anosUso;
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
        Motor other = (Motor) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Motor [id=" + id + ", version=" + version + ", fabricante=" + fabricante + ", potencia=" + potencia
                + ", tipo=" + tipo + ", anosUso=" + anosUso + "]";
    }
}