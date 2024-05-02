package isi.dan.practicas.practica1.service.persistence;

import java.util.List;
import java.util.Optional;

import isi.dan.practicas.practica1.exception.RecursoNoEncontradoException;

// https://www.baeldung.com/java-dao-pattern
public interface DAO<T> {
    public void guardar(T t) throws RecursoNoEncontradoException;
    Optional<T> buscarPorId(Integer id) throws RecursoNoEncontradoException;
    public List<T> listar();
    public void baja(Integer id) throws RecursoNoEncontradoException;
} 
