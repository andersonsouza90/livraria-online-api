package com.livrariaapi.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.livrariaapi.dto.QuantidadeDeLivrosDTO;
import com.livrariaapi.model.Autor;
import com.livrariaapi.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long>{
	
	void deleteByAutor(Autor autor);

	@Query("select new com.livrariaapi.dto.QuantidadeDeLivrosDTO("
			+ "a.autor.nome, "
			+ "count(*), "
			+ "round(count(*) * 100.0 / (select count(*) from Livro a2) * 1.0, 2) as percentage)"
			+ " from Livro a "
			+ "group by a.autor")
	List<QuantidadeDeLivrosDTO> relatorioQuantidadeDeLivros();

}