package isi.dan.practicas.practica1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import isi.dan.practicas.practica1.exception.CupoExcedidoException;
import isi.dan.practicas.practica1.exception.DocenteExcedidoException;
import isi.dan.practicas.practica1.model.Alumno;
import isi.dan.practicas.practica1.model.Curso;
import isi.dan.practicas.practica1.model.Docente;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)			
class Practica1ApplicationTests {

	String server;
	@LocalServerPort Integer port;
	RestTemplate restClient;
	String partialUrl;

	List<Alumno> alumnos;
	List<Docente> docentes;
	List<Curso> cursos;

	@BeforeEach
	public void beforeAll() {

		server = "http://localhost:";
		partialUrl = server + Integer.toString(port);
		restClient = new RestTemplate();

		alumnos = new ArrayList<>();
		docentes = new ArrayList<>();
		cursos = new ArrayList<>();
		
		alumnos.add(new Alumno("Federico Pacheco", 26056));
		alumnos.add(new Alumno("Facundo Velazco", 25025));
		alumnos.add(new Alumno("Sebastián Albino", 26025));
		
		docentes.add(new Docente("Martín Dominguez", 1000000.0));
		
		cursos.add(new Curso("Desarrollo de aplicaciones en la nube", 6, 50));
		cursos.add(new Curso("Desarrollo de aplicaciones móviles", 6, 1));
	}

	// https://www.baeldung.com/spring-rest-template-list  // No me funcionan los post sobre listas de objetos como se muestra acá
	// https://www.baeldung.com/spring-resttemplate-post-json
	@Test
	void casoGeneral() throws CupoExcedidoException, DocenteExcedidoException {
		
		HttpHeaders h = new HttpHeaders();
    	h.setContentType(MediaType.APPLICATION_JSON);

		// Alumnos
		for (Alumno a: alumnos) {
			Integer idRes = restClient.postForObject(partialUrl + "/alumno", new HttpEntity<Alumno>(a, h), Integer.class);
			a.setId(idRes);
		}

		restClient.delete(partialUrl + "/alumno/2");
		alumnos.remove(2);

		Alumno r1 = restClient.getForObject(partialUrl + "/alumno/0", Alumno.class); 
		assertEquals(alumnos.get(0), r1);
		
		Alumno[] r2 = restClient.getForObject(partialUrl + "/alumno/all", Alumno[].class); 
		assertArrayEquals(alumnos.toArray(new Alumno[0]), r2);
		

		// Docentes
		for (Docente d: docentes) {
			Integer idRes = restClient.postForObject(partialUrl + "/docente", new HttpEntity<Docente>(d, h), Integer.class);
			d.setId(idRes);
		}

		Docente[] r3 = restClient.getForObject(partialUrl + "/docente/all", Docente[].class); 
		assertArrayEquals(docentes.toArray(new Docente[0]), r3);


		// Cursos
		for (Curso c: cursos) {
			Integer idRes = restClient.postForObject(partialUrl + "/curso", new HttpEntity<Curso>(c, h), Integer.class);
			c.setId(idRes);
		}

		Curso[] r4 = restClient.getForObject(partialUrl + "/curso/all", Curso[].class); 
		assertArrayEquals(cursos.toArray(new Curso[0]), r4);
		
		for (Curso c: cursos) {
			for (Alumno a: alumnos) {
				restClient.put(partialUrl + "/curso/" + c.getId() + "/alumno/" + a.getId(), new HttpEntity<>(null, h));
				try {
					c.inscribirAlumno(a);
					a.addCurso(c);
				} catch(CupoExcedidoException e) { /* No hacer nada */ } 
			}

			for (Docente d: docentes) {
				restClient.put(partialUrl + "/curso/" + c.getId() + "/docente/" + d.getId(), new HttpEntity<>(null, h));
				try {
					d.addCursoDictado(c);
					c.setDocenteAsignado(d);
				} catch(DocenteExcedidoException e) { /* No hacer nada */ } 
			}
		}
		
		Curso[] r5 = restClient.getForObject(partialUrl + "/curso/all", Curso[].class); 
		assertArrayEquals(cursos.reversed().toArray(new Curso[0]), r5);
	}
}
