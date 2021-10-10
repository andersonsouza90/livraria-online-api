package com.livrariaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemRelatorioPercentualLivros {
	
	private String nome;
	private Long quantidade;
	private double percentual;
}
