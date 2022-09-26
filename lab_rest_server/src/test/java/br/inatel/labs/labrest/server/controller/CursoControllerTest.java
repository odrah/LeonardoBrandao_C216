package br.inatel.labs.labrest.server.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.inatel.labs.labrest.server.model.Curso;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CursoControllerTest {
	
	@Autowired
	private WebTestClient webTestClient;
	
	@Test
	void deveListarCursos() {
		webTestClient.get()
			.uri("/curso")
			.exchange()
			.expectStatus()
				.isOk()
			.expectBody()
				.returnResult();
		
	}
	
	
	@Test
	void dadoCursoIdValido_quandoGetCursoPorId_entaoRespondeComCursoValido() {
		
		Long cursoIdValido = 1L;
		
		Curso cursoRespondido = webTestClient.get()
			.uri("/curso" + cursoIdValido)
			.exchange()
			.expectStatus()
				.isOk()
			.expectBody(Curso.class)
				.returnResult()
				.getResponseBody();
		
		
		assertNotNull(cursoRespondido);
		assertEquals(cursoRespondido.getId(), cursoIdValido);
		
	}
	
	@Test
	void dadoCursoIdValido_quandoGetCursoPorId_entaoRespondeComCursoValido_post() {
		
		Long cursoIdValido = 99L;
		
		webTestClient.post()
			.uri("/curso" + cursoIdValido)
			.exchange()
			.expectStatus()
				.isNotFound();
		
	}
	
	   @Test
	    void dadoNovoCursoValido_PostCurso_ResponseComStatusCreated(){
	        Curso novoCurso = new Curso();
	        novoCurso.setDescricao("REST com Spring Boot e Spring WebFlux");
	        novoCurso.setCargaHoraria(120);

	        Curso cursoRespondido = webTestClient.post()
	                .uri("/curso")
	                .bodyValue(novoCurso)
	                .exchange()
	                .expectStatus().isCreated()
	                .expectBody(Curso.class)
	                .returnResult().getResponseBody();

	        assertThat(cursoRespondido).isNotNull();
	        assertThat(cursoRespondido.getId()).isNotNull();
	    }

	    @Test
	    void dadoCursoValido_PutCurso_ResponseComStatusAccepted() {
	        Curso cursoExistente = new Curso(1L, "REST e Spring Boot e Spring WebFlux", 120);

	         webTestClient
	                .put()
	                .uri("/curso")
	                .bodyValue(cursoExistente)
	                .exchange()
	                .expectStatus().isAccepted();
	    }

	    @Test
		void dadoValido_quandoDeleteCursoId_ResponseComStatusDeleted() {
			Long cursoIdValidoRemove = 1L;
		
			Curso cursoRespondido = webTestClient.delete()
				.uri("/curso/" + cursoIdValidoRemove)
				.exchange()
				.expectStatus()
					.isNoContent()
				.expectBody(Curso.class)
					.returnResult()
					.getResponseBody();
			
			assertThat(cursoRespondido).isNull();
		}

	    @Test
	    void dadoCursoInvalido_DeletarCursoPeloId_responderStatusNotFound(){
	        Long cursoIdInvalidoRemove = 99L;

	        webTestClient
	                .delete()
	                .uri("/curso/" + cursoIdInvalidoRemove)
	                .exchange()
	                .expectStatus().isNotFound();
	    }
}


	
	


