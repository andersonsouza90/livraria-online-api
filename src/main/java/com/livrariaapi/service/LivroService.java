package com.livrariaapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.livrariaapi.dto.LivroDto;
import com.livrariaapi.model.Livro;

@Service
public class LivroService {
	
	private ModelMapper modelMapper = new ModelMapper();
	private List<Livro> livros = new ArrayList<>();
	
	public List<LivroDto> listar() {
		return livros.stream()
				.map(t -> modelMapper.map(t, LivroDto.class)).collect(Collectors.toList());
	}

	public void cadastrar(LivroDto dto) {
		Livro livro = modelMapper.map(dto, Livro.class);
		livro.setId((long) new Random().nextInt(100));
		livros.add(livro);
	}

}
