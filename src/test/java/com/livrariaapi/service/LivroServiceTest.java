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
import org.springframework.test.context.ActiveProfiles;

import com.livrariaapi.dto.LivroDto;
import com.livrariaapi.dto.LivroDtoForm;
import com.livrariaapi.repository.AutorRepository;
import com.livrariaapi.repository.LivroRepository;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test") // para usar o properties-test
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
	LivroDtoForm livroFormDto = getTransacaoFormDto();
    LivroDto livroDto = livroService.cadastrar(livroFormDto);

    Mockito.verify(livroRepository).save(Mockito.any());

    assertEquals(livroFormDto.getTitulo(), livroDto.getTitulo());
    assertEquals(livroFormDto.getDataLancamento(), livroDto.getDataLancamento());
    assertEquals(livroFormDto.getNumeroPaginas(), livroDto.getNumeroPaginas());
    assertEquals(livroFormDto.getAutorId(), livroDto.getAutor().getId());
  }

  @Test
  void naoDeveriaCadastrarLivroComAutorInexistente() {
	  LivroDtoForm livroFormDto = getTransacaoFormDto();
    Mockito.when(autorRepository.getById(livroFormDto.getAutorId())).thenThrow(EntityNotFoundException.class);
    assertThrows(IllegalArgumentException.class, () -> livroService.cadastrar(livroFormDto));
  }

}