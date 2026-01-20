alter table usuario add column ativo tinyint;
update usuario set ativo = 1;