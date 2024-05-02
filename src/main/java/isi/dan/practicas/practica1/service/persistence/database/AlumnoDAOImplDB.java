package isi.dan.practicas.practica1.service.persistence.database;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isi.dan.practicas.practica1.exception.RecursoNoEncontradoException;
import isi.dan.practicas.practica1.model.Alumno;
import isi.dan.practicas.practica1.service.persistence.DAO;

// Con JPA esta clase no tiene mucho sentido. Solo sirve para lanzar excepciones,
// y enmascarar los metodos originales con nombres en ingles
@Service
public class AlumnoDAOImplDB implements DAO<Alumno> {

    @Autowired
    private AlumnoJPARepo ajr;

    @Override
    public void guardar(Alumno alumno) throws RecursoNoEncontradoException {
        ajr.save(alumno);
    }

    @Override
    public Optional<Alumno> buscarPorId(Integer id) throws RecursoNoEncontradoException {
        Optional<Alumno> opt = ajr.findById(id);
        if (opt.isEmpty()) 
            throw new RecursoNoEncontradoException(id, "Alumno");
        else
            // No tiene demasiado sentido retornar un opcional que siempre tiene un resultado,
            // pero así lo pide la consigna jaja
            return opt;
    }

    @Override
    public List<Alumno> listar() {
        return ajr.findAll();
    }

    @Override
    public void baja(Integer id) throws RecursoNoEncontradoException {
        if(ajr.existsById(id))
            throw new RecursoNoEncontradoException(id, "Alumno");
        else
            ajr.deleteById(id);
    }
    
}
