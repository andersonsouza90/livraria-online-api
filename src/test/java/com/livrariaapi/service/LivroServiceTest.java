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
import org.modelmapper.ModelMapper;

import com.livrariaapi.dto.AutorDto;
import com.livrariaapi.dto.LivroDto;
import com.livrariaapi.dto.LivroDtoForm;
import com.livrariaapi.model.Autor;
import com.livrariaapi.model.Livro;
import com.livrariaapi.repository.AutorRepository;
import com.livrariaapi.repository.LivroRepository;

@ExtendWith(MockitoExtension.class)
public class LivroServiceTest {

	@Mock
	private LivroRepository livroRepository;
	@Mock
	private AutorRepository autorRepository;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private LivroService livroService;

	private LivroDtoForm getLivro() {
		return new LivroDtoForm("Teste Cadastro de Livros", LocalDate.now(), 500, 1L);
	}

	@Test
	void deveriaCadastrarLivro() {
		LivroDtoForm livroDtoForm = new LivroDtoForm("Teste Cadastro de Livros", LocalDate.now(), 500, 1L);
		
		Autor autor = new Autor(livroDtoForm.getAutorId(), "Dandy", "dandy@gmail.com", LocalDate.now(), "Teste");
		AutorDto autorDto = new AutorDto(livroDtoForm.getAutorId(), "Dandy", "dandy@gmail.com", "Teste");

		Mockito.when(autorRepository.getById(livroDtoForm.getAutorId()))
				.thenReturn(autor);
		
		Livro livro = new Livro("Teste Cadastro de Livros", LocalDate.now(), 500, autor);
		
		Mockito
			.when(modelMapper.map(livroDtoForm, Livro.class))
			.thenReturn(livro);
		
		Mockito
		.when(modelMapper.map(livro, LivroDto.class))
		.thenReturn(new LivroDto(livro.getId(),
					livro.getTitulo(),
					livro.getLancamento(),
					livro.getNumeroDePaginas(),
					autorDto));

		LivroDto livroDto = livroService.cadastrar(livroDtoForm);

		Mockito.verify(livroRepository).save(Mockito.any());

		assertEquals(livroDtoForm.getTitulo(), livroDto.getTitulo());
		assertEquals(livroDtoForm.getLancamento(), livroDto.getLancamento());
		assertEquals(livroDtoForm.getNumeroDePaginas(), livroDto.getNumeroDePaginas());
		assertEquals(livroDtoForm.getAutorId(), livroDto.getAutor().getId());
	}

	@Test
	void naoDeveriaCadastrarLivroComAutorInexistente() {
		LivroDtoForm livroFormDto = getLivro();
		Mockito.when(autorRepository.getById(livroFormDto.getAutorId())).thenThrow(EntityNotFoundException.class);
		assertThrows(IllegalArgumentException.class, () -> livroService.cadastrar(livroFormDto));
	}

}