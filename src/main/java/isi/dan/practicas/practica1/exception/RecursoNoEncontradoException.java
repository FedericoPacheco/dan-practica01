package isi.dan.practicas.practica1.exception;

public class RecursoNoEncontradoException extends Exception {
    public RecursoNoEncontradoException(Integer id, String recursoClass) {
        super("No existe el id = " + id + " del modelo " + recursoClass);
    }
}
