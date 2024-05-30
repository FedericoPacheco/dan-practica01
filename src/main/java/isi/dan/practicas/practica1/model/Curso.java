package isi.dan.practicas.practica1.model;

import java.util.LinkedList;
import java.util.List;

//import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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
// @JsonManagedReference y @JsonBackReference no funcionan con relaciones muchos a muchos, solo uno a muchos o muchos a uno.
// JsonIgnore o JsonIgnoreProperties ignora (no serializa) desde uno de los lados, pero no es lo ideal.
// JsonIdentityInfo evita la recursión infinita reemplazando un objeto que ya se expandió por su id.
// https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
// https://stackoverflow.com/questions/41260579/hibernate-many-to-many-and-json-serialization
// https://stackoverflow.com/questions/37302816/how-to-use-jsonidentityinfo-with-circular-references
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Curso.class)
public class Curso {
    
    @Id
    @Column(name = "id_curso")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE, generator = "seq_curso")
    @SequenceGenerator(name = "seq_curso", initialValue = 0, sequenceName = "seq_curso", schema = "practica1")
    private Integer id;
    
    private String nombre;
    
    private Integer creditos;
    
    private Integer cupo;
    
    //@JsonBackReference
    @ManyToOne 
    @JoinColumn(name = "id_docente")
    private Docente docenteAsignado;
   
    @ManyToMany
    @JoinTable(
        name = "cursos_alumnos",
        joinColumns = @JoinColumn(name = "id_curso"),
        inverseJoinColumns = @JoinColumn(name = "id_alumno"),
        schema = "practica1"
    )
    private List<Alumno>listaInscriptos;
    
    public Curso() {
        this.id = null;
        this.nombre = "";
        this.creditos = 0;
        this.cupo = 0;
        this.docenteAsignado = null;
        this.listaInscriptos = new LinkedList<>();
    }

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
    public void setDocenteAsignado(Docente docenteAsignado) throws DocenteExcedidoException { 
        if (this.docenteAsignado == null || docenteAsignado.getCantidadCursosDictados() < Docente.MAX_CURSOS)
            this.docenteAsignado = docenteAsignado;    
        else throw new DocenteExcedidoException(docenteAsignado.getNombre());
    }
    public List<Alumno> getListaInscriptos() {
        return listaInscriptos;
    }
    public void setListaInscriptos(List<Alumno> listaInscriptos) {
        this.listaInscriptos = listaInscriptos;
    }
    
    @Override
    public String toString() {
        return "Curso [id=" + id + ", nombre=" + nombre + ", creditos=" + creditos + ", cupo=" + cupo
                + ", docenteAsignado=" + docenteAsignado + ", listaInscriptos=" + listaInscriptos + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((creditos == null) ? 0 : creditos.hashCode());
        result = prime * result + ((cupo == null) ? 0 : cupo.hashCode());
        result = prime * result + ((docenteAsignado == null) ? 0 : docenteAsignado.hashCode());
        result = prime * result + ((listaInscriptos == null) ? 0 : listaInscriptos.hashCode());
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
        Curso other = (Curso) obj;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (creditos == null) {
            if (other.creditos != null)
                return false;
        } else if (!creditos.equals(other.creditos))
            return false;
        if (cupo == null) {
            if (other.cupo != null)
                return false;
        } else if (!cupo.equals(other.cupo))
            return false;
        if (docenteAsignado == null) {
            if (other.docenteAsignado != null)
                return false;
        } else if (!docenteAsignado.equals(other.docenteAsignado))
            return false;
        if (listaInscriptos == null) {
            if (other.listaInscriptos != null)
                return false;
        } else if (!listaInscriptos.equals(other.listaInscriptos))
            return false;
        return true;
    }
}
