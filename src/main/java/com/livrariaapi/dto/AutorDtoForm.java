package com.livrariaapi.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AutorDtoForm {

	@NotBlank
	private String nome;
	
	@NotBlank
	private String email;
	
	@Past
	private LocalDate nascimento;
	
	@NotBlank
	private String miniCurriculo;
}

