CREATE TABLE autores
(
  id SERIAL primary key,
  nome varchar(100)  NOT NULL,
  email varchar(100)  NOT NULL,
  dt_nascimento date,
  mini_curriculo varchar(1000)
)