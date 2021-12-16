create table autores(
	id serial NOT NULL PRIMARY KEY,
	nome varchar(100) not null,
	email varchar(100) not null,
	nascimento date not null,
	mini_curriculo varchar(200) not null
	);
	
	