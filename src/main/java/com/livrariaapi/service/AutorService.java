package com.livrariaapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.livrariaapi.dto.AutorDto;
import com.livrariaapi.model.Autor;

@Service
public class AutorService {
	
	private ModelMapper modelMapper = new ModelMapper();
	private List<Autor> autores = new ArrayList<>();
	
	public List<AutorDto> listar() {
		return autores
				.stream()
				.map(t -> modelMapper.map(t, AutorDto.class)).collect(Collectors.toList());
	}

	public void cadastrar(AutorDto dto) {
		Autor autor = modelMapper.map(dto, Autor.class);
		autor.setId((long) new Random().nextInt(100));
		autores.add(autor);
	}
	
}
