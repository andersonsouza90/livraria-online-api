package com.livrariaapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.livrariaapi.dto.ItemRelatorioPercentualLivros;
import com.livrariaapi.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long> {
	
	/*
	 * @Query("select new br.com.alura.livrariaapi.dto.ItemLivrariaDto("
            + "l.autor.nome, count(*), round(count(*) * 1.0 / (select count(*) from Livro) * 100.0,1)    as percentual) "
            + " from Livro l group by l.autor order by percentual desc")
	 * */
	
	@Query(" select new com.livrariaapi.dto.ItemRelatorioPercentualLivros( \r\n"
			+ "	a.nome,\r\n"
			+ "	(select count(*) from Livro l where l.autor = a.id),\r\n"
			+ "	(select count(*) from Livro l where l.autor = a.id) * 1.0 / \r\n"
			+ "	   (select count(*) from Livro l ) as total ) \r\n"
			+ "    from Autor a")
	List<ItemRelatorioPercentualLivros> relatorioPercentualLivros();

}
