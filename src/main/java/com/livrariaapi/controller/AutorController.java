package com.livrariaapi.controller;

import java.net.URI;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.livrariaapi.dto.AtualizarAutorFormDto;
import com.livrariaapi.dto.AutorDto;
import com.livrariaapi.dto.AutorDtoForm;
import com.livrariaapi.service.AutorService;


@RestController
@RequestMapping("/autores")
public class AutorController {

	@Autowired
	private AutorService service;

	@GetMapping
	public Page<AutorDto> listar(@PageableDefault(size = 10) Pageable paginacao) {
		return service.listar(paginacao);

	}

	@PostMapping
	public ResponseEntity<AutorDto> cadastrar(@RequestBody @Valid AutorDtoForm dto,
			UriComponentsBuilder uriBuilder) {
		AutorDto autorDTO = service.cadastrar(dto);
		
		URI uri = uriBuilder.path("autores/{id}").buildAndExpand(autorDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(autorDTO);	
	}
	
	@PutMapping
	public ResponseEntity<AutorDto> atualizar(@RequestBody @Valid AtualizarAutorFormDto dto) {
		AutorDto atualizado = service.atualizar(dto);
		
		return ResponseEntity.ok(atualizado);
		
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<AutorDto> remover(@PathVariable @NotNull Long id) {
		service.deletar(id);
		
		return ResponseEntity.noContent().build();
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AutorDto> detalhar(@PathVariable @NotNull Long id) {
		AutorDto dto = service.consultar(id);
		
		return ResponseEntity.ok(dto);
		
	}
	
}
