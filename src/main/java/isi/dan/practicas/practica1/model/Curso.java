package isi.dan.practicas.practica1.model;

import java.util.LinkedList;
import java.util.List;

import isi.dan.practicas.practica1.exception.CupoExcedidoException;
import isi.dan.practicas.practica1.exception.DocenteExcedidoException;

public class Curso {
    private Integer id;
    private String nombre;
    private Integer creditos;
    private Integer cupo;
    private Integer docenteAsignado;
    private List<Integer>listaInscriptos;
    
    public Curso(String nombre, Integer creditos, Integer cupo) {
        this.id = null;
        this.nombre = nombre;
        this.creditos = creditos;
        this.cupo = cupo;
        this.docenteAsignado = null;
        this.listaInscriptos = new LinkedList<>();
    }

    public Curso inscribirAlumno(Alumno alumno) throws CupoExcedidoException {

        if (this.listaInscriptos.size() < this.cupo)
        {
            this.listaInscriptos.add(alumno.getId());
            return this;
        }
        else throw new CupoExcedidoException(this.nombre, this.cupo);
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
    public Integer getCreditos() {
        return creditos;
    }
    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }
    public Integer getCupo() {
        return cupo;
    }
    public void setCupo(Integer cupo) {
        this.cupo = cupo;
    }
    public Integer getDocenteAsignado() {
        return docenteAsignado;
    }

    public void setDocenteAsignado(Docente docenteAsignado) throws DocenteExcedidoException{
        if (docenteAsignado.getCantidadCursosDictados() < Docente.MAX_CURSOS)
            this.docenteAsignado = docenteAsignado.getId();    
        else throw new DocenteExcedidoException(docenteAsignado.getNombre());
    }

    public List<Integer> getListaInscriptos() {
        return listaInscriptos;
    }
    
    @Override
    public String toString() {
        return "Curso [id=" + id + ", nombre=" + nombre + ", creditos=" + creditos + ", cupo=" + cupo
                + ", docenteAsignado=" + docenteAsignado + ", listaInscriptos=" + listaInscriptos + "]";
    }
}
