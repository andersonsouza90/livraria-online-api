package com.livrariaapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livrariaapi.dto.ItemRelatorioPercentualLivros;
import com.livrariaapi.repository.AutorRepository;

@Service
public class RelatorioService {
	
	@Autowired
	private AutorRepository repository;

	public List<ItemRelatorioPercentualLivros> relatorioPercentualLivros() {
		return repository.relatorioPercentualLivros();
	}

}
