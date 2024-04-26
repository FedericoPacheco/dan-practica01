package isi.dan.practicas.practica1.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import isi.dan.practicas.practica1.exception.RecursoNoEncontradoException;
import isi.dan.practicas.practica1.model.Docente;

@Service
public class DocenteDAOImpl implements DAO<Docente>{

    private Integer contador_id;
    private List<Docente> listaDocentes;
    private static DocenteDAOImpl instance;

    // Se utiliza patron singleton
    private DocenteDAOImpl() {
        this.contador_id = 0;
        this.listaDocentes = new ArrayList<>();
    }

    public static DocenteDAOImpl getInstance() {
        if (instance == null)
            instance = new DocenteDAOImpl();
        return instance;
    }

    @Override
    public void guardar(Docente docente) throws RecursoNoEncontradoException{
        if (docente.getId() == null)
            docente.setId(this.contador_id++);
        else 
            this.baja(docente.getId());
        this.listaDocentes.add(docente);
    }

    @Override
    public Optional<Docente> buscarPorId(Integer id) throws RecursoNoEncontradoException{
        return Optional.ofNullable(
            listaDocentes.stream().filter(d -> d.getId() == id).findFirst()
            .orElseThrow(() -> new RecursoNoEncontradoException(id, "Docente"))
        );
    }

    @Override
    public List<Docente> listar() {
        return this.listaDocentes;
    }

    @Override
    public void baja(Integer id) throws RecursoNoEncontradoException {
        this.listaDocentes.remove(this.buscarPorId(id).get());
    }
    
}
