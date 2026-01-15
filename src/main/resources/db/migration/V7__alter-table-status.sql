ALTER TABLE status
    ADD COLUMN id_veiculo INT;

ALTER TABLE status
    ADD CONSTRAINT fk_status_veiculo
    FOREIGN KEY (id_veiculo)
    REFERENCES veiculo (id_veiculo);
