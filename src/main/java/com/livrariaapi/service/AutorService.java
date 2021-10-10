package com.livrariaapi.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.livrariaapi.dto.AutorDto;
import com.livrariaapi.dto.AutorDtoForm;
import com.livrariaapi.model.Autor;
import com.livrariaapi.repository.AutorRepository;

@Service
public class AutorService {
	
	private ModelMapper modelMapper = new ModelMapper();
	@Autowired
	AutorRepository repository;
	
	public Page<AutorDto> listar(Pageable paginacao) {
		Page<Autor> autores = repository.findAll(paginacao);
		
		return autores
				.map(a -> modelMapper.map(a, AutorDto.class));
	}
	
	@Transactional
	public AutorDto cadastrar(AutorDtoForm dto) {
		Autor autor = modelMapper.map(dto, Autor.class);
		autor.setId(null);
		repository.save(autor);
		return modelMapper.map(autor, AutorDto.class);
	}
	
}
