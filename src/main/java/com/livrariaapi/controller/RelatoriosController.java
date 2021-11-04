package com.livrariaapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.livrariaapi.dto.ItemRelatorioPercentualLivros;
import com.livrariaapi.service.RelatorioService;

@RestController
@RequestMapping("/relatorios")
public class RelatoriosController {
	
	private final RelatorioService relatorioService;

	public RelatoriosController(RelatorioService relatorioService) {
		this.relatorioService = relatorioService;
	}
	
	@GetMapping("/percentualLivros")
	public List<ItemRelatorioPercentualLivros> relatorioPercentualLivros(){
		return relatorioService.relatorioPercentualLivros();
	}
	
}
