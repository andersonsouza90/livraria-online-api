package com.livrariaapi.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AutorDtoForm {
	
	private long id;
	@NotBlank
	private String nome;
	@Email
	@NotBlank
	private String email;
	
	@NotNull
	@Past
	private LocalDate dataNascimento;
	
	private String miniCurriculo;
	
}
