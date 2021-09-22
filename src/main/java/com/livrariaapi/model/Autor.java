package com.livrariaapi.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Autor {
	
	private String nome;
	private String email;
	private LocalDate dataNascimento;
	private String miniCurriculo;

}
