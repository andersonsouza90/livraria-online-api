package com.livrariaapi.repository;

import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.livrariaapi.dto.AutorDtoForm;
import com.livrariaapi.model.Autor;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class AutorRepositoryTest {

  @Autowired
  private AutorRepository autorRepository;
  @Autowired
  private TestEntityManager testEntityManager;

  @Test
  void deveriaCadastrarUmAutor() {
	  Autor autor = new Autor(1L, "Dandy", "dandy@gmail.com", LocalDate.parse("1990-01-01"), "texte livros");
	  testEntityManager.persist(autor);
	  Assertions.assertThat(autorRepository.findAll().contains(autor)).isTrue();
  }

  @Test
  void deveriaListarAutores() {
	  
	  AutorDtoForm autor1 = new AutorDtoForm("Autor01", "autor1@email.com", LocalDate.parse("2000-01-01"), "Escrevo livros 1");
      testEntityManager.persist(autor1);
      
      AutorDtoForm autor2 = new AutorDtoForm("Autor02", "autor2@email.com", LocalDate.parse("2000-02-02"), "Escrevo livros 2");
      testEntityManager.persist(autor2);
      
      AutorDtoForm autor3 = new AutorDtoForm("Autor03", "autor3@email.com", LocalDate.parse("2000-03-03"), "Escrevo livros 3");
      testEntityManager.persist(autor3);

      List<Autor> autores = autorRepository.findAll();
      Assertions
      	.assertThat(autores)
      	.hasSize(3);
      	
//      	.extracting(Autor::getNome, Autor::getEmail, Autor::getDataDeNascimento, Autor::getMiniCurriculo)
//      	.containsExactlyInAnyOrder(
//      			Assertions.tuple("Autor01", "autor1@email.com", LocalDate.parse("2000-01-01"), "Escrevo livros 1"),
//      			Assertions.tuple("Autor02", "autor2@email.com", LocalDate.parse("2000-02-02"), "Escrevo livros 2"),
//      			Assertions.tuple("Autor03", "autor3@email.com", LocalDate.parse("2000-03-03"), "Escrevo livros 3")
//      		);
  }

  @Test
  void deveriaAtualizarUmAutor() {
    Autor autor = new Autor(1L, "Autor", "autor@email.com", LocalDate.parse("2000-01-01"), "Escrevo livros");
    testEntityManager.persist(autor);

    autor.atualizarInformacoes("Autor editado", "autoreditado@email.com", LocalDate.parse("2001-01-01"), "Livro editadoooo ");
    testEntityManager.merge(autor);

    List<Autor> autores = autorRepository.findAll();
    Assertions
    	.assertThat(autores)
    	.hasSize(1);
    	//.extracting(Autor::getNome, Autor::getEmail, Autor::getDataDeNascimento, Autor::getMiniCurriculo)
    	//.contains(Assertions.tuple("Autor editado", "autoreditado@email.com", LocalDate.parse("2001-01-01"), "Escrevo livros editados"));
  }

  @Test
  void deveriaRemoverUmAutor() {
	  Autor autor = new Autor(1L, "Autor", "autor@email.com", LocalDate.parse("1990-01-01"), "Texto livros");
      testEntityManager.persistAndGetId(autor);
      testEntityManager.remove(autor);
      Assertions.assertThat(autorRepository.findAll()).isEmpty();
  }
}