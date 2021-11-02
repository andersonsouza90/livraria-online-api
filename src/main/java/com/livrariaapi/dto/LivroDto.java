package com.livrariaapi.dto;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import com.livrariaapi.model.Autor;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LivroDto {
	
	private long id;
	@NotBlank
	@Size(min = 10)
	private String titulo;
	
	@PastOrPresent
	private LocalDate dataLancamento;
	@NotNull
	@Min(100)
	private int numeroPaginas;
	private AutorDto autor;
}
