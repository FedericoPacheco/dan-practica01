package isi.dan.practicas.practica1.service.persistence.database;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isi.dan.practicas.practica1.exception.RecursoNoEncontradoException;
import isi.dan.practicas.practica1.model.Docente;
import isi.dan.practicas.practica1.service.persistence.DAO;

// Con JPA esta clase no tiene mucho sentido. Solo sirve para lanzar excepciones,
// y enmascarar los metodos originales con nombres en ingles
@Service
public class DocenteDAOImplDB implements DAO<Docente> {

    @Autowired
    private DocenteJPARepo djr;

    @Override
    public void guardar(Docente docente) throws RecursoNoEncontradoException {
        djr.save(docente);
    }

    @Override
    public Optional<Docente> buscarPorId(Integer id) throws RecursoNoEncontradoException {
        Optional<Docente> opt = djr.findById(id);
        if (opt.isEmpty()) 
            throw new RecursoNoEncontradoException(id, "Docente");
        else
            // No tiene demasiado sentido retornar un opcional que siempre tiene un resultado,
            // pero así lo pide la consigna jaja
            return opt;
    }

    @Override
    public List<Docente> listar() {
        return djr.findAll();
    }

    @Override
    public void baja(Integer id) throws RecursoNoEncontradoException {
        if(djr.existsById(id))
            throw new RecursoNoEncontradoException(id, "Docente");
        else
            djr.deleteById(id);
    }
    
}
