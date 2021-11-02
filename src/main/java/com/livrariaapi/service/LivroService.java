package com.livrariaapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.livrariaapi.dto.AtualizarLivroFormDto;
import com.livrariaapi.dto.LivroConsultarDto;
import com.livrariaapi.dto.LivroDto;
import com.livrariaapi.dto.LivroDtoForm;
import com.livrariaapi.model.Autor;
import com.livrariaapi.model.Livro;
import com.livrariaapi.repository.AutorRepository;
import com.livrariaapi.repository.LivroRepository;

@Service
public class LivroService {
	
	private ModelMapper modelMapper = new ModelMapper();
	
	@Autowired
	LivroRepository repository;
	
	@Autowired
	AutorRepository autorRepository;
	
	public Page<LivroDto> listar(Pageable paginacao) {
		Page<Livro> livros = repository.findAll(paginacao);
		
		return livros
				.map(l -> modelMapper.map(l, LivroDto.class));
	}

	@Transactional
	public LivroDto cadastrar(LivroDtoForm dto) {
		
		try {
			Long idAutor = dto.getAutorId();
			Autor autor = autorRepository.getById(idAutor);

			if (Objects.isNull(autor)) {
				throw new IllegalArgumentException("Autor inexistente!!");
			}
			
			Livro livro = modelMapper.map(dto, Livro.class);
			livro.setId(null);
			livro.setAutor(autor);
			repository.save(livro);
			return modelMapper.map(livro, LivroDto.class);
			
			
		} catch (EntityNotFoundException e) {
			throw new IllegalArgumentException("Autor inexistente!");
		}
		
		
	}

	@Transactional
	public LivroDto atualizar(AtualizarLivroFormDto dto) {
		Livro l = repository.getById(dto.getId());
		l.atualizarInformacoes(dto.getTitulo(), dto.getDataLancamento(), dto.getNumeroPaginas());
		return modelMapper.map(l, LivroDto.class);
	}
	
	@Transactional
	public void deletar(Long id) {
		repository.deleteById(id);
	}

	public LivroConsultarDto consultar(Long id) {
		Livro l = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
		return modelMapper.map(l, LivroConsultarDto.class);
	}
	

}
