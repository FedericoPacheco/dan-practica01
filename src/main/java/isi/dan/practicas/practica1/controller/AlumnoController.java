package isi.dan.practicas.practica1.controller;

import java.util.List;

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

import isi.dan.practicas.practica1.exception.RecursoNoEncontradoException;
import isi.dan.practicas.practica1.model.Alumno;
import isi.dan.practicas.practica1.service.persistence.database.AlumnoDAOImplDB;

@Controller 
@RequestMapping("/alumno")
public class AlumnoController {
    @Autowired
    private AlumnoDAOImplDB adi;
    //private AlumnoDAOImplMem adi;

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Alumno> buscarPorId(@PathVariable Integer id) {
        Alumno response;
        try {
            response = adi.buscarPorId(id).get();
        } catch (RecursoNoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/all")
    @ResponseBody
    public ResponseEntity<List<Alumno>> listar() {
        return ResponseEntity.ok(adi.listar());
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Integer> guardar(@RequestBody Alumno alumno) {
        try {
            adi.guardar(alumno);
        } catch (RecursoNoEncontradoException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(alumno.getId());
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<Integer> modificar(@RequestBody Alumno alumno) {
        try {
            adi.guardar(alumno);
        } catch (RecursoNoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(alumno.getId());
    }


    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<String> baja(@PathVariable Integer id) {
        String response;
        try {
            adi.baja(id);
            response = "Alumno eliminado exitosamente";
        } catch (RecursoNoEncontradoException e) {
            response = e.getMessage();
        }
        return ResponseEntity.ok(response);
    }
}
