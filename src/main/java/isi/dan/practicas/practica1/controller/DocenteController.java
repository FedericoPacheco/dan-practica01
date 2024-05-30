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
import isi.dan.practicas.practica1.model.Docente;
import isi.dan.practicas.practica1.service.persistence.database.DocenteDAOImplDB;

@Controller 
@RequestMapping("/docente")
public class DocenteController {
    @Autowired
    private DocenteDAOImplDB ddi;
    //private DocenteDAOImplMem ddi;

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Docente> buscarPorId(@PathVariable Integer id) {
        Docente response;
        try {
            response = ddi.buscarPorId(id).get();
        } catch (RecursoNoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/all")
    @ResponseBody
    public ResponseEntity<List<Docente>> listar() {
        return ResponseEntity.ok(ddi.listar());
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Integer> guardar(@RequestBody Docente docente) {
        try {
            ddi.guardar(docente);
        } catch (RecursoNoEncontradoException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(docente.getId());
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<Integer> modificar(@RequestBody Docente docente) {
        try {
            ddi.guardar(docente);
        } catch (RecursoNoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(docente.getId());
    }


    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<String> baja(@PathVariable Integer id) {
        String response;
        try {
            ddi.baja(id);
            response = "Docente eliminado exitosamente";
        } catch (RecursoNoEncontradoException e) {
            response = e.getMessage();
        }
        return ResponseEntity.ok(response);
    }
}
