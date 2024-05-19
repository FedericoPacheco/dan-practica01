package isi.dan.practicas.practica1.service.persistence.database;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isi.dan.practicas.practica1.exception.RecursoNoEncontradoException;
import isi.dan.practicas.practica1.model.Curso;
import isi.dan.practicas.practica1.service.persistence.DAO;

// Con JPA esta clase no tiene mucho sentido. Solo sirve para lanzar excepciones,
// y enmascarar los metodos originales con nombres en ingles
@Service
public class CursoDAOImplDB implements DAO<Curso> {

    @Autowired
    private CursoJPARepo cjr;

    @Override
    public void guardar(Curso curso) throws RecursoNoEncontradoException {
        cjr.save(curso);
    }

    @Override
    public Optional<Curso> buscarPorId(Integer id) throws RecursoNoEncontradoException {
        Optional<Curso> opt = cjr.findById(id);
        if (opt.isEmpty()) 
            throw new RecursoNoEncontradoException(id, "Curso");
        else
            // No tiene demasiado sentido retornar un opcional que siempre tiene un resultado,
            // pero así lo pide la consigna jaja
            return opt;
    }

    @Override
    public List<Curso> listar() {
        return cjr.findAll();
    }

    @Override
    public void baja(Integer id) throws RecursoNoEncontradoException {
        if(!cjr.existsById(id))
            throw new RecursoNoEncontradoException(id, "Curso");
        else
            cjr.deleteById(id);
    }
}
