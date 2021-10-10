CREATE TABLE livros
(
  id SERIAL primary key,
  titulo varchar(100)  NOT NULL,
  dt_lancamento date NOT NULL,
  num_paginas int,
  autor_id int not null,
  constraint pk_livros foreign key (autor_id) references autores(id)
)