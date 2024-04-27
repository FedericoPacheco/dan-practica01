package isi.dan.practicas.practica1.model;

import java.util.LinkedList;
import java.util.List;

public class Alumno {
    private Integer id;
    private String nombre;
    private Integer legajo;
    private List<Integer> cursosInscriptos;
    
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
    public List<Integer> getCursosInscriptos() {
        return cursosInscriptos;
    }
    
    @Override
    public String toString() {
        return "Alumno [id=" + id + ", nombre=" + nombre + ", legajo=" + legajo + ", cursosInscriptos="
                + cursosInscriptos + "]";
    }

    public void addCurso(Curso c) {
        this.cursosInscriptos.add(c.getId());
    } 
}
