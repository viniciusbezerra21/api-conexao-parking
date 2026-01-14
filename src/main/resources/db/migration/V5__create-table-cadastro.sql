CREATE TABLE cadastro
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_veiculo INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario),
    FOREIGN KEY (id_veiculo) REFERENCES veiculo (id_veiculo)
);
