package isi.dan.practicas.practica1.model;

import java.util.LinkedList;
import java.util.List;

import isi.dan.practicas.practica1.exception.DocenteExcedidoException;

public class Docente {

    public static final Integer MAX_CURSOS = 3;

    private Integer id;
    private String nombre;
    private Double salario;
    private List<Integer> cursosDictados;
    
    public Docente(String nombre, Double salario) {
        this.id = null;
        this.nombre = nombre;
        this.salario = salario;
        this.cursosDictados = new LinkedList<>();
    }

    public Integer getCantidadCursosDictados() {
        return cursosDictados.size();
    }

    public void addCursoDictado(Curso curso) throws DocenteExcedidoException {
        if (this.cursosDictados.size() < Docente.MAX_CURSOS)
            this.cursosDictados.add(curso.getId());
        else throw new DocenteExcedidoException(this.nombre);
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
    public Double getSalario() {
        return salario;
    }
    public void setSalario(Double salario) {
        this.salario = salario;
    }
    public List<Integer> getCursosDictados() {
        return cursosDictados;
    }
    
    @Override
    public String toString() {
        return "Docente [id=" + id + ", nombre=" + nombre + ", salario=" + salario + ", cursosDictados="
                + cursosDictados + "]";
    }    
}
