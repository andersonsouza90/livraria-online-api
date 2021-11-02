package com.livrariaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.livrariaapi.model.Autor;
import com.livrariaapi.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long>{

	void deleteByAutor(Autor autor);

}
