package com.livrariaapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livrariaapi.dto.QuantidadeDeLivrosDTO;
import com.livrariaapi.repository.LivroRepository;

@Service
public class RelatorioService {
	
	@Autowired
	private LivroRepository repository;
	
	public List<QuantidadeDeLivrosDTO> relatorioQuantidadeDeLivros(){
		return repository.relatorioQuantidadeDeLivros();
	}

}
