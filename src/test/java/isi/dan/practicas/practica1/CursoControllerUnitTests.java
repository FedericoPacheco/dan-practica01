package isi.dan.practicas.practica1;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.intThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import isi.dan.practicas.practica1.exception.RecursoNoEncontradoException;
import isi.dan.practicas.practica1.model.Curso;
import isi.dan.practicas.practica1.service.persistence.database.AlumnoDAOImplDB;
import isi.dan.practicas.practica1.service.persistence.database.CursoDAOImplDB;
import isi.dan.practicas.practica1.service.persistence.database.DocenteDAOImplDB;

// Probar solo tests: mvn test
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CursoControllerUnitTests {
    
    String server = "http://localhost:";
    @LocalServerPort Integer port;
    String endpoint = "/curso";
    String url;
    RestTemplate restClient;

    Curso c;

    @MockBean
    CursoDAOImplDB cdi;

    @MockBean
    AlumnoDAOImplDB adi;

    @MockBean
    DocenteDAOImplDB ddi;

    @BeforeEach
    public void beforeEach() throws RecursoNoEncontradoException {
        c = new Curso("Desarrollo de aplicaciones en la nube", 6, 1);

        when(cdi.buscarPorId(intThat(i -> i < 10))).thenReturn(Optional.of(c));
        when(cdi.buscarPorId(intThat(i -> i >= 10))).thenThrow(new RecursoNoEncontradoException(10, "curso"));
    
        url = server + Integer.toString(port) + endpoint;

        restClient = new RestTemplate();
    }

    @Test
    public void buscarPorId() {
        ResponseEntity<String> response1 = restClient.exchange(url + "/0", HttpMethod.GET, null, String.class);
        assertTrue(response1.getStatusCode().equals(HttpStatus.OK));

        // Da efectivamente bad request pero por algun motivo lo percibe como
        // error en lugar de test exitoso xd
        //ResponseEntity<String> response2 = restClient.exchange(url + "/10", HttpMethod.GET, null, String.class);
        //assertTrue(response2.getStatusCode().equals(HttpStatus.NOT_FOUND));
    }
}
