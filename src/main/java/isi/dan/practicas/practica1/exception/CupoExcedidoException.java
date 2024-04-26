package isi.dan.practicas.practica1.exception;

public class CupoExcedidoException extends Exception {
    public CupoExcedidoException(String nombreCurso, Integer cupoMaxAlumnos) {
        super("El curso " + nombreCurso + " alcanzó el cupo máximo para alumnos inscriptos (" + cupoMaxAlumnos + ")");
    }
}
