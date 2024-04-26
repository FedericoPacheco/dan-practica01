package isi.dan.practicas.practica1.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import isi.dan.practicas.practica1.exception.RecursoNoEncontradoException;
import isi.dan.practicas.practica1.model.Alumno;

@Service
public class AlumnoDAOImpl implements DAO<Alumno> {

    private Integer contador_id;
    private List<Alumno> listaAlumnos;
    private static AlumnoDAOImpl instance;

    // Se utiliza patron singleton
    private AlumnoDAOImpl() {
        this.contador_id = 0;
        this.listaAlumnos = new ArrayList<>();
    }

    public static AlumnoDAOImpl getInstance() {
        if (instance == null)
            instance = new AlumnoDAOImpl(); 
        return instance;
    }

    @Override
    public void guardar(Alumno alumno) throws RecursoNoEncontradoException{
        if (alumno.getId() == null)
            alumno.setId(this.contador_id++);
        else 
            this.baja(alumno.getId());
        this.listaAlumnos.add(alumno);
    }

    @Override
    public Optional<Alumno> buscarPorId(Integer id) throws RecursoNoEncontradoException {
        return Optional.ofNullable(
            listaAlumnos.stream().filter(a -> a.getId() == id).findFirst()
            .orElseThrow(() -> new RecursoNoEncontradoException(id, "Alumno"))
        );
    }

    @Override
    public List<Alumno> listar() {
        return listaAlumnos;
    }

    @Override
    public void baja(Integer id) throws RecursoNoEncontradoException {
        this.listaAlumnos.remove(this.buscarPorId(id).get());
    }
    
}
