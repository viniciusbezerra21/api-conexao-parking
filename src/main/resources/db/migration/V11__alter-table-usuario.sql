alter table usuario add column role varchar(20);

update usuario set role = 'ROLE_USER';

alter table usuario modify role varchar(20) not null;

update usuario
set role = 'ROLE_ADMIN'
where id_usuario = 4;