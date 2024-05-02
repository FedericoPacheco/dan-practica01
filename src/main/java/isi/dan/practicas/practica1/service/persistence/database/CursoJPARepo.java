package isi.dan.practicas.practica1.service.persistence.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import isi.dan.practicas.practica1.model.Curso;

@Repository
public interface CursoJPARepo extends JpaRepository<Curso, Integer> {
    
}
