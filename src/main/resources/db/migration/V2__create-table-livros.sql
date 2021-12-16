create table livros(
	id serial NOT NULL PRIMARY KEY,
	titulo varchar(100) not null,
	lancamento date not null,
	numero_de_paginas int not null,
	autor_id bigint not null,
	foreign key (autor_id) references autores (id)
	);
	
	