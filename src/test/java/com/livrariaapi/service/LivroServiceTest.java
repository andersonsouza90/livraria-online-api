package com.livrariaapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.livrariaapi.dto.LivroDto;
import com.livrariaapi.dto.LivroDtoForm;
import com.livrariaapi.model.Autor;
import com.livrariaapi.repository.AutorRepository;
import com.livrariaapi.repository.LivroRepository;

@ExtendWith(MockitoExtension.class)
public class LivroServiceTest {

  @Mock
  private LivroRepository livroRepository;
  @Mock
  private AutorRepository autorRepository;

  @InjectMocks
  private LivroService livroService;

  private LivroDtoForm getTransacaoFormDto() {
    return new LivroDtoForm("Teste Cadastro de Livros", LocalDate.now(), 500, 1L);
  }

  @Test
  void deveriaCadastrarLivro() {
	LivroDtoForm livroDtoForm = getTransacaoFormDto();
    
    Mockito
    	.when(autorRepository.getById(livroDtoForm.getAutorId()))
    	.thenReturn(new Autor(livroDtoForm.getAutorId(), "Dandy", "dandy@gmail.com", LocalDate.now(), "Teste"));
    
    LivroDto livroDto = livroService.cadastrar(livroDtoForm);
    
    Mockito.verify(livroRepository).save(Mockito.any());
    

    assertEquals(livroDtoForm.getTitulo(), livroDto.getTitulo());
    assertEquals(livroDtoForm.getDataLancamento(), livroDto.getDataLancamento());
    assertEquals(livroDtoForm.getNumeroPaginas(), livroDto.getNumeroPaginas());
    assertEquals(livroDtoForm.getAutorId(), livroDto.getAutor().getId());
  }

  @Test
  void naoDeveriaCadastrarLivroComAutorInexistente() {
	  LivroDtoForm livroFormDto = getTransacaoFormDto();
    Mockito.when(autorRepository.getById(livroFormDto.getAutorId())).thenThrow(EntityNotFoundException.class);
    assertThrows(IllegalArgumentException.class, () -> livroService.cadastrar(livroFormDto));
  }

}