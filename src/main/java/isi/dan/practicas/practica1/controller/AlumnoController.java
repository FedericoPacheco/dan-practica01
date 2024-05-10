package isi.dan.practicas.practica1.controller;

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
import isi.dan.practicas.practica1.service.persistence.memory.AlumnoDAOImplMem;

@Controller 
@RequestMapping("/alumno")
public class AlumnoController {
    @Autowired
    private AlumnoDAOImplDB adi;
    //private AlumnoDAOImplMem adi;

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<String> buscarPorId(@PathVariable Integer id) {
        String response;
        try {
            response = adi.buscarPorId(id).toString();
        } catch (RecursoNoEncontradoException e) {
            response = e.getMessage();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/all")
    @ResponseBody
    public ResponseEntity<String> listar() {
        return ResponseEntity.ok(adi.listar().toString());
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> guardar(@RequestBody Alumno alumno) {
        String response;
        try {
            adi.guardar(alumno);
            response = "Alumno guardado exitosamente";
        } catch (RecursoNoEncontradoException e) {
            response = e.getMessage();
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<String> modificar(@RequestBody Alumno alumno) {
        String response;
        try {
            adi.guardar(alumno);
            response = "Alumno modificado exitosamente";
        } catch (RecursoNoEncontradoException e) {
            response = e.getMessage();
        }
        return ResponseEntity.ok(response);
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
