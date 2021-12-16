package com.livrariaapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.livrariaapi.dto.QuantidadeDeLivrosDTO;
import com.livrariaapi.service.RelatorioService;


@RestController
@RequestMapping("/relatorios")
public class RelatoriosController {

	@Autowired
	private RelatorioService service;
	
	@GetMapping("/livraria")
	public List<QuantidadeDeLivrosDTO> relatorioQuantidadeDeLivros(){
		return service.relatorioQuantidadeDeLivros();
	}
}
