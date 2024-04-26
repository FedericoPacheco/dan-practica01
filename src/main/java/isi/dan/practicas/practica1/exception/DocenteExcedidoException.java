package isi.dan.practicas.practica1.exception;

import isi.dan.practicas.practica1.model.Docente;

public class DocenteExcedidoException extends Exception {
    public DocenteExcedidoException(String nombreDocente) {
        super("El docente " + nombreDocente + " ya tiene " + Docente.MAX_CURSOS + " cursos asignados");
    }
}
