package isi.dan.practicas.practica1.model;

import java.util.LinkedList;
import java.util.List;

import isi.dan.practicas.practica1.exception.CupoExcedidoException;
import isi.dan.practicas.practica1.exception.DocenteExcedidoException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "curso", schema = "practica1")
public class Curso {
    
    @Id
    @Column(name = "id_curso")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE, generator = "seq_alumno")
    @SequenceGenerator(name = "seq_alumno", initialValue = 0, sequenceName = "seq_alumno")
    private Integer id;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "creditos")
    private Integer creditos;
    
    @Column(name = "cupo")
    private Integer cupo;
    
    @Column(name = "docente_asignado")
    @ManyToOne 
    @JoinColumn(name = "id_docente")
    private Docente docenteAsignado;
   
    @Column(name = "lista_inscriptos")
    @ManyToMany
    @JoinTable(
        name = "cursos_alumnos",
        joinColumns = @JoinColumn(name = "id_curso"),
        inverseJoinColumns = @JoinColumn(name = "id_alumno")
    )
    private List<Alumno>listaInscriptos;
    
    public Curso(String nombre, Integer creditos, Integer cupo) {
        this.id = null;
        this.nombre = nombre;
        this.creditos = creditos;
        this.cupo = cupo;
        this.docenteAsignado = null;
        this.listaInscriptos = new LinkedList<>();
    }

    public Curso inscribirAlumno(Alumno alumno) throws CupoExcedidoException {

        if (this.listaInscriptos.size() < this.cupo) {
            this.listaInscriptos.add(alumno);
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
    public Docente getDocenteAsignado() {
        return docenteAsignado;
    }

    public void setDocenteAsignado(Docente docenteAsignado) throws DocenteExcedidoException{
        if (docenteAsignado.getCantidadCursosDictados() < Docente.MAX_CURSOS)
            this.docenteAsignado = docenteAsignado;    
        else throw new DocenteExcedidoException(docenteAsignado.getNombre());
    }

    public List<Alumno> getListaInscriptos() {
        return listaInscriptos;
    }
    
    @Override
    public String toString() {
        return "Curso [id=" + id + ", nombre=" + nombre + ", creditos=" + creditos + ", cupo=" + cupo
                + ", docenteAsignado=" + docenteAsignado + ", listaInscriptos=" + listaInscriptos + "]";
    }
}
