package isi.dan.practicas.practica1.model;

import java.util.LinkedList;
import java.util.List;

import isi.dan.practicas.practica1.exception.DocenteExcedidoException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "docente", schema = "practica1")
public class Docente {

    public static final Integer MAX_CURSOS = 1;

    @Id
    @Column(name = "id_docente")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE, generator = "seq_docente")
    @SequenceGenerator(name = "seq_docente", initialValue = 0, sequenceName = "seq_docente", schema = "practica1")
    private Integer id;

    private String nombre;
    
    private Double salario;
    
    @OneToMany(mappedBy = "docenteAsignado")
    private List<Curso> cursosDictados;
    
    public Docente() {
        this.id = null;
        this.nombre = "";
        this.salario = 0.0;
        this.cursosDictados = new LinkedList<>();
    }

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
            this.cursosDictados.add(curso);
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
    public List<Curso> getCursosDictados() {
        return cursosDictados;
    }
    
    @Override
    public String toString() {
        return "Docente [id=" + id + ", nombre=" + nombre + ", salario=" + salario + ", cursosDictados="
                + cursosDictados + "]";
    }    
}
