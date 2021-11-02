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
import com.livrariaapi.dto.AutorConsultarDto;
import com.livrariaapi.dto.AutorDto;
import com.livrariaapi.dto.AutorDtoForm;
import com.livrariaapi.service.AutorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/autores")
@Api(tags = "Autor")
public class AutorController {
	
	@Autowired
	private AutorService service;
	
	@GetMapping
	@ApiOperation("Listar autores")
	public Page<AutorDto> listar(@PageableDefault(size = 10) Pageable paginacao) {
		return service.listar(paginacao);
	}
	
	@PostMapping
	@ApiOperation("Cadastrar novo autor")
	public ResponseEntity<AutorDto> cadastrar(@RequestBody @Valid AutorDtoForm autor,
												UriComponentsBuilder uriBuilder) {
		
		AutorDto autorDto = service.cadastrar(autor);
		
		URI uri = uriBuilder
				.path("/autores/{id}")
				.buildAndExpand(autorDto.getId())
				.toUri();
		return ResponseEntity.created(uri).body(autorDto);
	}
	
	@PutMapping
	public ResponseEntity<AutorDto> atualizar(@RequestBody @Valid AtualizarAutorFormDto dto) {
		AutorDto atualizada = service.atualizar(dto);
		return ResponseEntity.ok(atualizada);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<AutorDto> deletar(@PathVariable @NotNull Long id) {
		service.deletar(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AutorConsultarDto> consultar(@PathVariable @NotNull Long id) {
		AutorConsultarDto dto = service.consultar(id);
		return ResponseEntity.ok(dto);
	}
	
}
