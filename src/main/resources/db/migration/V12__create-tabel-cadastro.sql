CREATE TABLE cadastro
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_veiculo INT NOT NULL,
    data_cadastro datetime not null ,
    FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario),
    FOREIGN KEY (id_veiculo) REFERENCES veiculo (id_veiculo)
);
