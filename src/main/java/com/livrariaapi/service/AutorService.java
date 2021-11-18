package com.livrariaapi.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.livrariaapi.dto.AtualizarAutorFormDto;
import com.livrariaapi.dto.AutorConsultarDto;
import com.livrariaapi.dto.AutorDto;
import com.livrariaapi.dto.AutorDtoForm;
import com.livrariaapi.model.Autor;
import com.livrariaapi.repository.AutorRepository;
import com.livrariaapi.repository.LivroRepository;

@Service
public class AutorService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AutorRepository repository;
	
	@Autowired
	private LivroRepository livroRepository;
	
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
	
	@Transactional
	public AutorDto atualizar(AtualizarAutorFormDto dto) {
		Autor a = repository.getById(dto.getId());
		a.atualizarInformacoes(dto.getNome(), dto.getEmail(), dto.getDataNascimento(), dto.getMiniCurriculo());
		return modelMapper.map(a, AutorDto.class);
	}
	
	@Transactional
	public void deletar(Long id) {
		livroRepository.deleteByAutor(repository.getById(id));
		repository.deleteById(id);
	}

	public AutorConsultarDto consultar(Long id) {
		Autor a = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
		return modelMapper.map(a, AutorConsultarDto.class);
	}
	
}
