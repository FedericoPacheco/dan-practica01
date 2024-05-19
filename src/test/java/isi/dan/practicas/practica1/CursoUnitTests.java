package isi.dan.practicas.practica1;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import isi.dan.practicas.practica1.exception.CupoExcedidoException;
import isi.dan.practicas.practica1.exception.DocenteExcedidoException;
import isi.dan.practicas.practica1.model.Alumno;
import isi.dan.practicas.practica1.model.Curso;
import isi.dan.practicas.practica1.model.Docente;

public class CursoUnitTests {
    
    Alumno a1, a2;
    Curso c1, c2;
    Docente d;

    @BeforeEach
    public void beforeEach() {
        a1 = new Alumno("Federico Pacheco", 26056);
        a2 = new Alumno("Facundo Velazco", 25025);
        c1 = new Curso("Desarollo de aplicaciones en la nube", 6, 1);
        c2 = new Curso("Desarrollo de aplicaciones móviles", 6, 1);
        d = new Docente("Martín Domínguez", 100000.0);
    }

    @Test
    public void inscribirAlumno() {
        assertDoesNotThrow(() -> c1.inscribirAlumno(a1));
        a1.addCurso(c1);
        assertThrows(CupoExcedidoException.class, () -> c1.inscribirAlumno(a2));
    }

    @Test
    public void setDocenteAsignado() throws DocenteExcedidoException {
        assertDoesNotThrow(() -> c1.setDocenteAsignado(d));
        d.addCursoDictado(c1);
        assertThrows(DocenteExcedidoException.class, () -> c2.setDocenteAsignado(d));
    }
}
