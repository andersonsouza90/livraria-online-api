package com.livrariaapi.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.livrariaapi.dto.LivroDto;
import com.livrariaapi.dto.LivroDtoForm;
import com.livrariaapi.model.Livro;
import com.livrariaapi.repository.LivroRepository;

@Service
public class LivroService {
	
	private ModelMapper modelMapper = new ModelMapper();
	
	@Autowired
	LivroRepository repository;
	
	public Page<LivroDto> listar(Pageable paginacao) {
		Page<Livro> livros = repository.findAll(paginacao);
		
		return livros
				.map(l -> modelMapper.map(l, LivroDto.class));
	}

	@Transactional
	public LivroDto cadastrar(LivroDtoForm dto) {
		Livro livro = modelMapper.map(dto, Livro.class);
		livro.setId(null);
		repository.save(livro);
		return modelMapper.map(livro, LivroDto.class);
	}

}
