create table usuarios(
	id serial NOT NULL PRIMARY KEY,
	nome varchar(100) not null,
	login varchar(100) not null,
	senha varchar(100) not null
	);