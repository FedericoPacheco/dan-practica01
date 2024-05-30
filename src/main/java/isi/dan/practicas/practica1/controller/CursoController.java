package isi.dan.practicas.practica1.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import isi.dan.practicas.practica1.exception.CupoExcedidoException;
import isi.dan.practicas.practica1.exception.RecursoNoEncontradoException;
import isi.dan.practicas.practica1.model.Alumno;
import isi.dan.practicas.practica1.model.Curso;
import isi.dan.practicas.practica1.model.Docente;
import isi.dan.practicas.practica1.service.persistence.database.AlumnoDAOImplDB;
import isi.dan.practicas.practica1.service.persistence.database.CursoDAOImplDB;
import isi.dan.practicas.practica1.service.persistence.database.DocenteDAOImplDB;

@Controller 
@RequestMapping("/curso")
public class CursoController {
    @Autowired
    private CursoDAOImplDB cdi;
    //private CursoDAOImplMem cdi;

    @Autowired
    private AlumnoDAOImplDB adi;
    //private AlumnoDAOImplMem adi;

    @Autowired
    private DocenteDAOImplDB ddi;
    //private DocenteDAOImplMem ddi;

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Curso> buscarPorId(@PathVariable Integer id) {
        Curso response;
        try {
            response = cdi.buscarPorId(id).get();
        } catch (RecursoNoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/all")
    @ResponseBody
    public ResponseEntity<List<Curso>> listar() {
        return ResponseEntity.ok(cdi.listar());
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Integer> guardar(@RequestBody Curso curso) {
        try {
            cdi.guardar(curso);
        } catch (RecursoNoEncontradoException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(curso.getId());
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<Integer> modificar(@RequestBody Curso curso) {
        try {
            cdi.guardar(curso);
        } catch (RecursoNoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(curso.getId());
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<String> baja(@PathVariable Integer id) {
        String response;
        try {
            response = "Curso " + cdi.buscarPorId(id).get().getNombre() + " eliminado exitosamente";
            cdi.baja(id);
        } catch (RecursoNoEncontradoException | NoSuchElementException e) {
            response = e.getMessage();
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping (value = "/{idCurso}/alumno/{idAlumno}")
    public ResponseEntity<String> inscribirAlumno(@PathVariable Integer idCurso, @PathVariable Integer idAlumno) {
        String response;
        try {
            Curso c = cdi.buscarPorId(idCurso).get();
            Alumno a = adi.buscarPorId(idAlumno).get();
            c.inscribirAlumno(a);
            a.addCurso(c);
            cdi.guardar(c); // No olvidar en la DB. El curso es dueño de la relación many to many
            response = "Alumno " + a.getNombre() + " agregado exitosamente al curso " + c.getNombre();
        } catch (RecursoNoEncontradoException | NoSuchElementException | CupoExcedidoException e) {
            response = e.getMessage();
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{idCurso}/docente/{idDocente}") 
    public ResponseEntity<String> setDocenteAsignado(@PathVariable Integer idCurso, @PathVariable Integer idDocente) { 
        String response;
        try {
            Curso c = cdi.buscarPorId(idCurso).get();
            Docente d = ddi.buscarPorId(idDocente).get();
            c.setDocenteAsignado(d);
            d.addCursoDictado(c);
            cdi.guardar(c); // No olvidar guardar en la DB. El curso "recuerda" quien es su docente
            response = "Docente " + d.getNombre() + " agregado exitosamente al curso " + c.getNombre();
        } catch (Exception e) {
            response = e.getMessage();
        }
        return ResponseEntity.ok(response);
    }

}
