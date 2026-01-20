alter table veiculo add column excluido tinyint;
update veiculo set excluido = 0;