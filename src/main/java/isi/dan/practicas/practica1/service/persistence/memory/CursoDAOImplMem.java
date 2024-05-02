package isi.dan.practicas.practica1.service.persistence.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import isi.dan.practicas.practica1.exception.RecursoNoEncontradoException;
import isi.dan.practicas.practica1.model.Curso;
import isi.dan.practicas.practica1.service.persistence.DAO;

@Service
public class CursoDAOImplMem implements DAO<Curso> {
    // Para mi inscribir el alumno y asignar el docente no son responsabilidades del dao

    private Integer contador_id;
    private List<Curso> listaCursos;

    public CursoDAOImplMem() {
        this.contador_id = 0;
        this.listaCursos = new ArrayList<>();
    } 

    @Override
    public void guardar(Curso curso) throws RecursoNoEncontradoException{
        if (curso.getId() == null)
            curso.setId(this.contador_id++);
        else 
            this.baja(curso.getId());
        this.listaCursos.add(curso);
    }

    @Override
    public Optional<Curso> buscarPorId(Integer id) throws RecursoNoEncontradoException{
        return Optional.ofNullable(
            listaCursos.stream().filter(d -> d.getId() == id).findFirst()
            .orElseThrow(() -> new RecursoNoEncontradoException(id, "Curso"))
        );
    }

    @Override
    public List<Curso> listar() {
        return this.listaCursos;
    }

    @Override
    public void baja(Integer id) throws RecursoNoEncontradoException {
        this.listaCursos.remove(this.buscarPorId(id).get());
    }
    
}
