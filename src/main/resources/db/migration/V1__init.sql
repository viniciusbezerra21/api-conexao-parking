CREATE TABLE usuario
(
    id_usuario        INT AUTO_INCREMENT PRIMARY KEY,
    email_corporativo VARCHAR(150) NOT NULL,
    senha             VARCHAR(255) NOT NULL
);


CREATE TABLE proprietario
(
    id_proprietario  INT AUTO_INCREMENT PRIMARY KEY,
    nome             VARCHAR(150) NOT NULL,
    cpf_proprietario VARCHAR(11)  NOT NULL UNIQUE
);


CREATE TABLE veiculo
(
    id_veiculo      INT AUTO_INCREMENT PRIMARY KEY,
    numero_placa    VARCHAR(10) NOT NULL UNIQUE,
    cor             VARCHAR(50) NOT NULL,
    visitante       BOOLEAN DEFAULT FALSE,
    tipo_veiculo    INT         NOT NULL,
    id_proprietario INT         NOT NULL,
    FOREIGN KEY (id_proprietario) REFERENCES proprietario (id_proprietario)
);


CREATE TABLE movimentacao
(
    id_movimentacao INT AUTO_INCREMENT PRIMARY KEY,
    id_veiculo      INT      NOT NULL,
    data_entrada    DATETIME NOT NULL,
    data_saida      DATETIME,
    FOREIGN KEY (id_veiculo) REFERENCES veiculo (id_veiculo)
);


CREATE TABLE cadastros
(
    id_usuario INT NOT NULL,
    id_veiculo INT NOT NULL,
    PRIMARY KEY (id_usuario, id_veiculo),
    FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario),
    FOREIGN KEY (id_veiculo) REFERENCES veiculo (id_veiculo)
);

