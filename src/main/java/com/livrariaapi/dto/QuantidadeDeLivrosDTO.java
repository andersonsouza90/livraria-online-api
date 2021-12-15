package com.livrariaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuantidadeDeLivrosDTO {
	
	private String autor;
	private Long quantidade;
	private Double percentual;

}