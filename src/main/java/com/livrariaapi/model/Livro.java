package com.livrariaapi.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Livro {
	
	private String titulo;
	private LocalDate dataLancamento;
	private int numeroPaginas;
	private Autor autor;
}
