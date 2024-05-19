package isi.dan.practicas.practica1;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.intThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

import isi.dan.practicas.practica1.exception.RecursoNoEncontradoException;
import isi.dan.practicas.practica1.model.Curso;
import isi.dan.practicas.practica1.service.persistence.database.CursoDAOImplDB;
import isi.dan.practicas.practica1.service.persistence.database.CursoJPARepo;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class CursoDAOImplDBUnitTests {
    
    @MockBean
    CursoJPARepo cjrm;

    @Autowired
    CursoDAOImplDB cdi;
    
    Curso c;

    @BeforeEach
    public void beforeEach() {
        c = new Curso("Desarrollo de aplicaciones en la nube", 6, 1); 
    }

    @Test
    public void buscarPorId() {
        when(cjrm.findById(intThat(i -> i < 10))).thenReturn(Optional.of(c));
        when(cjrm.findById(intThat(i -> i >= 10))).thenReturn(Optional.empty());
        
        assertDoesNotThrow(() -> cdi.buscarPorId(0));
        assertThrows(RecursoNoEncontradoException.class, () -> cdi.buscarPorId(10));
    }

    @Test
    public void guardar() throws RecursoNoEncontradoException {
        when(cjrm.save(any(Curso.class))).thenReturn(c);
        cdi.guardar(c);
        verify(cjrm, times(1)).save(c);
    }
}
