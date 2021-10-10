package com.livrariaapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.livrariaapi.dto.ItemRelatorioPercentualLivros;
import com.livrariaapi.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long> {
	
	@Query(" select new com.livrariaapi.dto.ItemRelatorioPercentualLivros( \r\n"
			+ "	a.nome,\r\n"
			+ "	(select count(*) from Livro l where l.autor = a.id),\r\n"
			+ "	(select count(*) from Livro l where l.autor = a.id) * 1.0 / \r\n"
			+ "	   (select count(*) from Livro l ) as total ) \r\n"
			+ "    from Autor a")
	List<ItemRelatorioPercentualLivros> relatorioPercentualLivros();

}
