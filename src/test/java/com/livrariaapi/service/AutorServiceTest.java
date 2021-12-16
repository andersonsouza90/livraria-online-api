package com.livrariaapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.livrariaapi.dto.AutorDto;
import com.livrariaapi.dto.AutorDtoForm;
import com.livrariaapi.model.Autor;
import com.livrariaapi.repository.AutorRepository;

@ExtendWith(MockitoExtension.class)
class AutorServiceTest {
	
	@Mock
	private AutorRepository autorRepository;
	
	@InjectMocks
	private AutorService autorService;
	
	@Mock
	private ModelMapper modelMapper;

	@Test
	void deveriaCadastrarUmAutor() {
		AutorDtoForm formDTO = new AutorDtoForm(
				"Anderson", 
				"anderson@gmail.com",
				LocalDate.of(1997, 5, 28),
				"Livros sobre tudo"
				);
		
		Autor autor = new Autor(formDTO.getNome(),
				formDTO.getEmail(),
				formDTO.getNascimento(),
				formDTO.getMiniCurriculo());
		
		Mockito
		.when(modelMapper.map(formDTO, Autor.class))
		.thenReturn(autor);
		
		Mockito
		.when(modelMapper.map(autor, AutorDto.class))
		.thenReturn(new AutorDto(
				null,
				autor.getNome(),
				autor.getEmail(),
				autor.getMiniCurriculo()));
		
		
		AutorDto dto = autorService.cadastrar(formDTO);
		
		Mockito.verify(autorRepository).save(Mockito.any());
		
		assertEquals(formDTO.getNome(), dto.getNome());
		assertEquals(formDTO.getEmail(), dto.getEmail());
		assertEquals(formDTO.getMiniCurriculo(), dto.getMiniCurriculo());
		
	}

}