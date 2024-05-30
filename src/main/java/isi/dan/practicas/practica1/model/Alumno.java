package isi.dan.practicas.practica1.model;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
//import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "alumno", schema = "practica1")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Alumno.class)
public class Alumno {

    @Id
    @Column(name = "id_alumno")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE, generator = "seq_alumno")
    @SequenceGenerator(name = "seq_alumno", initialValue = 0, sequenceName = "seq_alumno", schema = "practica1")
    private Integer id;
    
    private String nombre;

    private Integer legajo;
    
    //@JsonIgnore 
    @ManyToMany(mappedBy = "listaInscriptos")
    private List<Curso> cursosInscriptos;
    
    public Alumno() {
        this.id = null;
        this.nombre = "";
        this.legajo = 0;
        this.cursosInscriptos = new LinkedList<>();
    }

    public Alumno(String nombre, Integer legajo) {
        this.id = null;
        this.nombre = nombre;
        this.legajo = legajo;
        this.cursosInscriptos = new LinkedList<>();
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Integer getLegajo() {
        return legajo;
    }
    public void setLegajo(Integer legajo) {
        this.legajo = legajo;
    }
    public List<Curso> getCursosInscriptos() {
        return cursosInscriptos;
    }
    
    @Override
    public String toString() {
        return "Alumno [id=" + id + ", nombre=" + nombre + ", legajo=" + legajo + "]";
    }

    public void addCurso(Curso c) {
        this.cursosInscriptos.add(c);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((legajo == null) ? 0 : legajo.hashCode());
        result = prime * result + ((cursosInscriptos == null) ? 0 : cursosInscriptos.hashCode());
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
        Alumno other = (Alumno) obj;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (legajo == null) {
            if (other.legajo != null)
                return false;
        } else if (!legajo.equals(other.legajo))
            return false;
        /*
        if (cursosInscriptos == null) {
            if (other.cursosInscriptos != null)
                return false;
        } else if (!cursosInscriptos.equals(other.cursosInscriptos))
            return false;
        */
        return true;
    }   
}
