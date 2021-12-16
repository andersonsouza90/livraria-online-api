//package com.livrariaapi.repository;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import com.livrariaapi.model.Autor;
//
//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
//@ActiveProfiles("test")
//public class AutorRepositoryTest {
//
//  @Autowired
//  private AutorRepository autorRepository;
//  @Autowired
//  private TestEntityManager em;
//
//  @Test
//  void deveriaCadastrarUmAutor() {
//	  Autor autor = new Autor("Dandy", "dandy@gmail.com", LocalDate.parse("1990-01-01"), "Mini curriculo aqui...");
//	  em.persist(autor);
//	  Assertions.assertThat(autorRepository.findAll().contains(autor)).isTrue();
//  }
//
//  @Test
//  void deveriaListarAutores() {
//	  
//	  Autor autor1 = new Autor("Autor01", "autor1@email.com", LocalDate.parse("1990-01-01"), "Mini curriculo aqui...");
//      em.persist(autor1);
//      
//      Autor autor2 = new Autor("Autor02", "autor2@email.com", LocalDate.parse("1990-02-02"), "Mini curriculo aqui...");
//      em.persist(autor2);
//      
//      Autor autor3 = new Autor("Autor03", "autor3@email.com", LocalDate.parse("1990-03-03"), "Mini curriculo aqui...");
//      em.persist(autor3);
//
//      List<Autor> autores = autorRepository.findAll();
//      Assertions
//      	.assertThat(autores)
//      	.hasSize(4) // hasSize = 4 por que já tem um cadastrado no banco
//      	.extracting(Autor::getNome, Autor::getEmail, Autor::getDataNascimento, Autor::getMiniCurriculo)
//      	.containsExactlyInAnyOrder(
//	        Assertions.tuple("Autor01", "autor1@email.com", LocalDate.parse("1990-01-01"), "Mini curriculo aqui..."),
//	        Assertions.tuple("Autor02", "autor2@email.com", LocalDate.parse("1990-02-02"), "Mini curriculo aqui..."),
//	        Assertions.tuple("Autor03", "autor3@email.com", LocalDate.parse("1990-03-03"), "Mini curriculo aqui..."),
//	        Assertions.tuple("Dandy teste", "dandy@gmail.com", LocalDate.parse("1990-05-02"), "Mini curriculo aqui...")
//      	);
//  }
//
//  @Test
//  void deveriaAtualizarUmAutor() {
//    Autor autor = new Autor("Dandy", "autor@email.com", LocalDate.parse("1990-01-01"), "Mini curriculo aqui...");
//    em.persist(autor);
//
//    autor.atualizarInformacoes("Anderson Souza", "dandy@email.com", LocalDate.parse("1990-01-01"), "Mini curriculo aqui...");
//    em.merge(autor);
//
//    List<Autor> autores = autorRepository.findAll();
//    Assertions
//    	.assertThat(autores)
//    	.hasSize(2) // hasSize = 2 por que já tem um cadastrado no banco
//    	.extracting(Autor::getNome, Autor::getEmail, Autor::getDataNascimento, Autor::getMiniCurriculo)
//    	.containsExactlyInAnyOrder(
//    			Assertions.tuple("Anderson Souza", "dandy@email.com", LocalDate.parse("1990-01-01"), "Mini curriculo aqui..."),
//    			Assertions.tuple("Dandy teste", "dandy@gmail.com", LocalDate.parse("1990-05-02"), "Mini curriculo aqui...")
//    	);
//  }
//
//  @Test
//  void deveriaRemoverUmAutor() {
//	  Autor autor = new Autor("Dandy teste", "dandy@gmail.com", LocalDate.parse("1990-05-02"), "Mini curriculo aqui...");
//      Long id = (Long) em.persistAndGetId(autor);
//      em.remove(autor);
//      Assertions.assertThat(autorRepository.findById(id)).isEmpty();
//  }
//}