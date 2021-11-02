package com.livrariaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarLivroFormDto extends LivroDtoForm{
	
	private Long id;
}
