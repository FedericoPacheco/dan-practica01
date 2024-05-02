package isi.dan.practicas.practica1.model;

import java.util.LinkedList;
import java.util.List;

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
public class Alumno {

    @Id
    @Column(name = "id_alumno")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE, generator = "seq_alumno")
    @SequenceGenerator(name = "seq_alumno", initialValue = 0, sequenceName = "seq_alumno")
    private Integer id;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "legajo")
    private Integer legajo;
    
    @Column(name = "cursos_inscriptos")
    @ManyToMany(mappedBy = "listaInscriptos")
    private List<Curso> cursosInscriptos;
    
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
        return "Alumno [id=" + id + ", nombre=" + nombre + ", legajo=" + legajo + ", cursosInscriptos="
                + cursosInscriptos + "]";
    }

    public void addCurso(Curso c) {
        this.cursosInscriptos.add(c);
    } 
}
